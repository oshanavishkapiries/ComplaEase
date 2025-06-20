package com.cms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

/**
 * Complaint model class representing complaints in the Complaint Management
 * System
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Complaint {
    private int id;
    private String title;
    private String description;
    private ComplaintStatus status;
    private ComplaintPriority priority;
    private int userId;
    private String adminRemarks;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Additional fields for display purposes
    private String userName;
    private String userFullName;

    // Enums for complaint status and priority
    public enum ComplaintStatus {
        PENDING, IN_PROGRESS, RESOLVED
    }

    public enum ComplaintPriority {
        LOW, MEDIUM, HIGH
    }

    // Helper methods
    public boolean isResolved() {
        return ComplaintStatus.RESOLVED.equals(this.status);
    }

    public boolean isPending() {
        return ComplaintStatus.PENDING.equals(this.status);
    }

    public boolean isInProgress() {
        return ComplaintStatus.IN_PROGRESS.equals(this.status);
    }

    public boolean isHighPriority() {
        return ComplaintPriority.HIGH.equals(this.priority);
    }

    public String getStatusDisplayName() {
        switch (status) {
            case PENDING:
                return "Pending";
            case IN_PROGRESS:
                return "In Progress";
            case RESOLVED:
                return "Resolved";
            default:
                return status.toString();
        }
    }

    public String getPriorityDisplayName() {
        switch (priority) {
            case LOW:
                return "Low";
            case MEDIUM:
                return "Medium";
            case HIGH:
                return "High";
            default:
                return priority.toString();
        }
    }

    public String getStatusCssClass() {
        switch (status) {
            case PENDING:
                return "badge-warning";
            case IN_PROGRESS:
                return "badge-info";
            case RESOLVED:
                return "badge-success";
            default:
                return "badge-secondary";
        }
    }

    public String getPriorityCssClass() {
        switch (priority) {
            case LOW:
                return "badge-success";
            case MEDIUM:
                return "badge-warning";
            case HIGH:
                return "badge-danger";
            default:
                return "badge-secondary";
        }
    }
}