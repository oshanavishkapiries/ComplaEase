
export type User = {
  id: string;
  name: string;
  email: string;
  avatar: string;
  role: 'student' | 'instructor' | 'admin';
};

export type Course = {
  id: string;
  title: string;
  description: string;
  instructor: {
    id: string;
    name: string;
    avatar: string;
  };
  thumbnail: string;
  lectures: Lecture[];
  tags: string[];
  category: string;
  createdAt: string;
  updatedAt: string;
  enrolledStudents: number;
  rating: number;
};

export type Lecture = {
  id: string;
  title: string;
  description: string;
  videoUrl: string;
  duration: number; // in seconds
  createdAt: string;
  resources?: {
    title: string;
    url: string;
    type: 'pdf' | 'link' | 'code';
  }[];
};

export type Comment = {
  id: string;
  content: string;
  user: {
    id: string;
    name: string;
    avatar: string;
  };
  createdAt: string;
  lectureId: string;
  parentId?: string;
  replies?: Comment[];
};

export const currentUser: User = {
  id: 'user1',
  name: 'John Doe',
  email: 'john.doe@example.com',
  avatar: 'https://ui-avatars.com/api/?name=John+Doe',
  role: 'student',
};

export const instructors: User[] = [
  {
    id: 'instructor1',
    name: 'Dr. Jane Smith',
    email: 'jane.smith@university.edu',
    avatar: 'https://ui-avatars.com/api/?name=Jane+Smith',
    role: 'instructor',
  },
  {
    id: 'instructor2',
    name: 'Prof. Michael Johnson',
    email: 'michael.johnson@university.edu',
    avatar: 'https://ui-avatars.com/api/?name=Michael+Johnson',
    role: 'instructor',
  },
  {
    id: 'instructor3',
    name: 'Dr. Sarah Williams',
    email: 'sarah.williams@university.edu',
    avatar: 'https://ui-avatars.com/api/?name=Sarah+Williams',
    role: 'instructor',
  },
];

export const courses: Course[] = [
  {
    id: 'course1',
    title: 'Introduction to Computer Science',
    description: 'Learn the fundamentals of computer science, including algorithms, data structures, and programming basics.',
    instructor: instructors[0],
    thumbnail: 'https://images.unsplash.com/photo-1461749280684-dccba630e2f6?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60',
    lectures: [
      {
        id: 'lecture1',
        title: 'What is Computer Science?',
        description: 'An overview of the field and what to expect in this course.',
        videoUrl: 'https://example.com/videos/cs-intro',
        duration: 1800,
        createdAt: '2023-01-15T10:00:00Z',
      },
      {
        id: 'lecture2',
        title: 'Algorithms: The Building Blocks',
        description: 'Understanding algorithmic thinking and problem-solving.',
        videoUrl: 'https://example.com/videos/algorithms',
        duration: 2400,
        createdAt: '2023-01-17T10:00:00Z',
      }
    ],
    tags: ['Computer Science', 'Programming', 'Beginner'],
    category: 'Computer Science',
    createdAt: '2023-01-15T09:00:00Z',
    updatedAt: '2023-04-20T15:00:00Z',
    enrolledStudents: 2456,
    rating: 4.7,
  },
  {
    id: 'course2',
    title: 'Advanced Physics: Quantum Mechanics',
    description: 'Explore the fascinating world of quantum mechanics and its applications in modern physics.',
    instructor: instructors[1],
    thumbnail: 'https://images.unsplash.com/photo-1498050108023-c5249f4df085?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60',
    lectures: [
      {
        id: 'lecture1',
        title: 'Quantum Fundamentals',
        description: 'Understanding the basic principles of quantum mechanics.',
        videoUrl: 'https://example.com/videos/quantum-basics',
        duration: 2700,
        createdAt: '2023-02-10T11:00:00Z',
      }
    ],
    tags: ['Physics', 'Quantum Mechanics', 'Advanced'],
    category: 'Physics',
    createdAt: '2023-02-10T10:00:00Z',
    updatedAt: '2023-05-15T14:30:00Z',
    enrolledStudents: 1895,
    rating: 4.9,
  },
  {
    id: 'course3',
    title: 'Data Analysis with Python',
    description: 'Learn how to analyze and visualize data using Python programming language and popular libraries.',
    instructor: instructors[2],
    thumbnail: 'https://images.unsplash.com/photo-1488590528505-98d2b5aba04b?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60',
    lectures: [
      {
        id: 'lecture1',
        title: 'Introduction to Python for Data Analysis',
        description: 'Getting started with Python for data analysis and manipulation.',
        videoUrl: 'https://example.com/videos/python-data-intro',
        duration: 2100,
        createdAt: '2023-03-05T09:30:00Z',
      }
    ],
    tags: ['Python', 'Data Analysis', 'Programming'],
    category: 'Data Science',
    createdAt: '2023-03-05T08:00:00Z',
    updatedAt: '2023-06-10T11:15:00Z',
    enrolledStudents: 3241,
    rating: 4.8,
  },
  {
    id: 'course4',
    title: 'Introduction to Psychology',
    description: 'Explore the human mind and behavior in this introductory psychology course.',
    instructor: instructors[0],
    thumbnail: 'https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60',
    lectures: [
      {
        id: 'lecture1',
        title: 'The History of Psychology',
        description: 'Understanding how psychology evolved as a scientific discipline.',
        videoUrl: 'https://example.com/videos/psych-history',
        duration: 2400,
        createdAt: '2023-01-20T13:00:00Z',
      }
    ],
    tags: ['Psychology', 'Social Science', 'Beginner'],
    category: 'Psychology',
    createdAt: '2023-01-20T12:00:00Z',
    updatedAt: '2023-04-25T10:45:00Z',
    enrolledStudents: 4752,
    rating: 4.6,
  },
  {
    id: 'course5',
    title: 'Machine Learning Fundamentals',
    description: 'Learn the core concepts of machine learning and how to implement basic algorithms.',
    instructor: instructors[1],
    thumbnail: 'https://images.unsplash.com/photo-1581091226825-a6a2a5aee158?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60',
    lectures: [
      {
        id: 'lecture1',
        title: 'What is Machine Learning?',
        description: 'An introduction to machine learning concepts and applications.',
        videoUrl: 'https://example.com/videos/ml-intro',
        duration: 1950,
        createdAt: '2023-02-15T14:00:00Z',
      }
    ],
    tags: ['Machine Learning', 'AI', 'Data Science'],
    category: 'Computer Science',
    createdAt: '2023-02-15T13:00:00Z',
    updatedAt: '2023-05-20T16:20:00Z',
    enrolledStudents: 3578,
    rating: 4.8,
  },
  {
    id: 'course6',
    title: 'Environmental Science: Climate Change',
    description: 'Understanding the science behind climate change and its global impact.',
    instructor: instructors[2],
    thumbnail: 'https://images.unsplash.com/photo-1500673922987-e212871fec22?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60',
    lectures: [
      {
        id: 'lecture1',
        title: 'The Science of Climate',
        description: 'Understanding climate systems and how they operate.',
        videoUrl: 'https://example.com/videos/climate-science',
        duration: 2700,
        createdAt: '2023-03-10T11:30:00Z',
      }
    ],
    tags: ['Environmental Science', 'Climate Change', 'Earth Science'],
    category: 'Environmental Science',
    createdAt: '2023-03-10T10:30:00Z',
    updatedAt: '2023-06-15T09:50:00Z',
    enrolledStudents: 2134,
    rating: 4.7,
  },
  {
    id: 'course7',
    title: 'Introduction to Business Management',
    description: 'Learn the fundamentals of business management, including planning, organizing, and leading.',
    instructor: instructors[0],
    thumbnail: 'https://images.unsplash.com/photo-1501854140801-50d01698950b?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60',
    lectures: [
      {
        id: 'lecture1',
        title: 'Business Management Principles',
        description: 'Core principles and theories of effective business management.',
        videoUrl: 'https://example.com/videos/business-principles',
        duration: 2400,
        createdAt: '2023-01-25T15:00:00Z',
      }
    ],
    tags: ['Business', 'Management', 'Leadership'],
    category: 'Business',
    createdAt: '2023-01-25T14:00:00Z',
    updatedAt: '2023-04-30T12:15:00Z',
    enrolledStudents: 3845,
    rating: 4.5,
  },
  {
    id: 'course8',
    title: 'Web Development Bootcamp',
    description: 'A comprehensive course on modern web development technologies and practices.',
    instructor: instructors[1],
    thumbnail: 'https://images.unsplash.com/photo-1649972904349-6e44c42644a7?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60',
    lectures: [
      {
        id: 'lecture1',
        title: 'HTML and CSS Foundations',
        description: 'Learning the basics of web structure and styling.',
        videoUrl: 'https://example.com/videos/html-css-basics',
        duration: 2100,
        createdAt: '2023-02-20T10:30:00Z',
      }
    ],
    tags: ['Web Development', 'HTML', 'CSS', 'JavaScript'],
    category: 'Web Development',
    createdAt: '2023-02-20T09:30:00Z',
    updatedAt: '2023-05-25T11:40:00Z',
    enrolledStudents: 5243,
    rating: 4.9,
  }
];

export const comments: Comment[] = [
  {
    id: 'comment1',
    content: 'This lecture was incredibly helpful for understanding the basics!',
    user: {
      id: 'user1',
      name: 'John Doe',
      avatar: 'https://ui-avatars.com/api/?name=John+Doe',
    },
    createdAt: '2023-06-15T09:30:00Z',
    lectureId: 'lecture1',
    replies: [
      {
        id: 'comment2',
        content: 'I agree! The examples really made the concepts clear.',
        user: {
          id: 'user2',
          name: 'Emily Wilson',
          avatar: 'https://ui-avatars.com/api/?name=Emily+Wilson',
        },
        createdAt: '2023-06-15T10:15:00Z',
        lectureId: 'lecture1',
        parentId: 'comment1',
      }
    ]
  },
  {
    id: 'comment3',
    content: 'Could you please elaborate more on the quantum entanglement concept?',
    user: {
      id: 'user3',
      name: 'Michael Brown',
      avatar: 'https://ui-avatars.com/api/?name=Michael+Brown',
    },
    createdAt: '2023-06-16T14:45:00Z',
    lectureId: 'lecture1',
    replies: []
  }
];

export const categories = [
  'Computer Science',
  'Data Science',
  'Physics',
  'Psychology',
  'Business',
  'Environmental Science',
  'Web Development',
  'Mathematics',
  'History',
  'Literature',
  'Biology',
  'Chemistry',
  'Art & Design',
];

export const tags = [
  'Programming',
  'Beginner',
  'Advanced',
  'Machine Learning',
  'AI',
  'Web Development',
  'Data Analysis',
  'Climate Change',
  'Quantum Mechanics',
  'Psychology',
  'Business',
  'Management',
  'Leadership',
  'HTML',
  'CSS',
  'JavaScript',
  'Python',
  'Computer Science',
  'Social Science',
  'Earth Science',
];
