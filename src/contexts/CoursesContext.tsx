
import { createContext, useContext, useState, ReactNode } from "react";
import { courses, Course } from "@/lib/data";

type CoursesContextType = {
  courses: Course[];
  featuredCourses: Course[];
  isLoading: boolean;
  searchCourses: (query: string) => Course[];
  getCourseById: (id: string) => Course | undefined;
  filterCourses: (options: FilterOptions) => Course[];
};

type FilterOptions = {
  search?: string;
  instructor?: string;
  category?: string;
  tags?: string[];
  sortBy?: 'newest' | 'popular' | 'rating';
};

const CoursesContext = createContext<CoursesContextType | undefined>(undefined);

export const CoursesProvider = ({ children }: { children: ReactNode }) => {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  
  // Get top rated courses as featured
  const featuredCourses = [...courses]
    .sort((a, b) => b.rating - a.rating)
    .slice(0, 4);

  const searchCourses = (query: string): Course[] => {
    if (!query) return courses;
    
    const lowercaseQuery = query.toLowerCase();
    return courses.filter(
      (course) =>
        course.title.toLowerCase().includes(lowercaseQuery) ||
        course.description.toLowerCase().includes(lowercaseQuery) ||
        course.instructor.name.toLowerCase().includes(lowercaseQuery) ||
        course.tags.some((tag) => tag.toLowerCase().includes(lowercaseQuery)) ||
        course.category.toLowerCase().includes(lowercaseQuery)
    );
  };

  const getCourseById = (id: string): Course | undefined => {
    return courses.find((course) => course.id === id);
  };

  const filterCourses = (options: FilterOptions): Course[] => {
    let filteredCourses = [...courses];
    
    // Apply search filter
    if (options.search) {
      filteredCourses = searchCourses(options.search);
    }
    
    // Apply instructor filter
    if (options.instructor) {
      filteredCourses = filteredCourses.filter(
        (course) => course.instructor.id === options.instructor
      );
    }
    
    // Apply category filter
    if (options.category) {
      filteredCourses = filteredCourses.filter(
        (course) => course.category === options.category
      );
    }
    
    // Apply tags filter
    if (options.tags && options.tags.length > 0) {
      filteredCourses = filteredCourses.filter((course) =>
        options.tags?.some((tag) => course.tags.includes(tag))
      );
    }
    
    // Apply sorting
    if (options.sortBy) {
      switch (options.sortBy) {
        case 'newest':
          filteredCourses.sort(
            (a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
          );
          break;
        case 'popular':
          filteredCourses.sort(
            (a, b) => b.enrolledStudents - a.enrolledStudents
          );
          break;
        case 'rating':
          filteredCourses.sort((a, b) => b.rating - a.rating);
          break;
      }
    }
    
    return filteredCourses;
  };

  const value = {
    courses,
    featuredCourses,
    isLoading,
    searchCourses,
    getCourseById,
    filterCourses,
  };

  return <CoursesContext.Provider value={value}>{children}</CoursesContext.Provider>;
};

export const useCourses = () => {
  const context = useContext(CoursesContext);
  if (context === undefined) {
    throw new Error("useCourses must be used within a CoursesProvider");
  }
  return context;
};
