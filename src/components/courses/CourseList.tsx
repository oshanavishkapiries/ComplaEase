
import { Course } from "@/lib/data";
import { CourseCard } from "./CourseCard";
import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Grid2X2, List } from "lucide-react";

interface CourseListProps {
  courses: Course[];
  isLoading?: boolean;
}

export function CourseList({ courses, isLoading = false }: CourseListProps) {
  const [viewMode, setViewMode] = useState<"grid" | "list">("grid");
  
  if (isLoading) {
    return (
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 animate-pulse">
        {[...Array(6)].map((_, i) => (
          <div key={i} className="h-96 rounded-lg bg-slate-200 dark:bg-slate-800" />
        ))}
      </div>
    );
  }
  
  if (courses.length === 0) {
    return (
      <div className="text-center py-10">
        <h3 className="text-lg font-medium">No courses found</h3>
        <p className="text-muted-foreground">Try adjusting your search or filter criteria</p>
      </div>
    );
  }
  
  return (
    <div className="space-y-6">
      <div className="flex justify-end">
        <div className="flex rounded-md border overflow-hidden">
          <Button
            variant={viewMode === "grid" ? "default" : "ghost"}
            size="sm"
            className="rounded-none"
            onClick={() => setViewMode("grid")}
          >
            <Grid2X2 size={16} />
          </Button>
          <Button
            variant={viewMode === "list" ? "default" : "ghost"}
            size="sm"
            className="rounded-none"
            onClick={() => setViewMode("list")}
          >
            <List size={16} />
          </Button>
        </div>
      </div>
      
      {viewMode === "grid" ? (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {courses.map((course) => (
            <CourseCard key={course.id} course={course} />
          ))}
        </div>
      ) : (
        <div className="flex flex-col space-y-4">
          {courses.map((course) => (
            <div key={course.id} className="border rounded-lg p-4 flex gap-4">
              <img
                src={course.thumbnail}
                alt={course.title}
                className="w-32 h-24 object-cover rounded-md"
              />
              <div className="flex-1">
                <h3 className="font-medium">{course.title}</h3>
                <p className="text-sm text-muted-foreground line-clamp-2">{course.description}</p>
                <div className="flex mt-2 gap-4 text-sm">
                  <span className="text-muted-foreground">
                    {course.instructor.name}
                  </span>
                  <span className="text-muted-foreground">{course.lectures.length} lectures</span>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
