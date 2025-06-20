package edu.cms.dao;

import edu.cms.repository.ComplaintRepository;
import edu.cms.repository.UserRepository;

import javax.sql.DataSource;

public class DAOFactory {

    private static DAOFactory instance;
    private final DataSource dataSource;
    private UserRepository userRepository;
    private ComplaintRepository complaintRepository;

    private DAOFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static synchronized DAOFactory getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new DAOFactory(dataSource);
        }
        return instance;
    }

    public UserRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = new UserDAOImpl(dataSource);
        }
        return userRepository;
    }

    public ComplaintRepository getComplaintRepository() {
        if (complaintRepository == null) {
            complaintRepository = new ComplaintDAOImpl(dataSource);
        }
        return complaintRepository;
    }

    public void close() {
        System.out.println("DAOFactory closed");
    }
}