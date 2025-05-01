
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Course } from "@/lib/data";
import { Link } from "react-router-dom";
import { Star, Clock, Users } from "lucide-react";

interface CourseCardProps {
  course: Course;
}

export function CourseCard({ course }: CourseCardProps) {
  // Format date to a readable format
  const formattedDate = new Date(course.createdAt).toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'short',
  });
  
  // Calculate total course duration in hours
  const totalDuration = course.lectures.reduce((acc, lecture) => acc + lecture.duration, 0);
  const totalHours = Math.round(totalDuration / 3600);
  
  return (
    <Link to={`/courses/${course.id}`} className="block h-full">
      <Card className="course-card h-full transition-all hover:translate-y-[-5px]">
        <div className="relative aspect-video w-full overflow-hidden">
          <img
            src={course.thumbnail}
            alt={course.title}
            className="object-cover w-full h-full"
          />
          <div className="absolute top-2 right-2">
            <Badge variant="secondary" className="flex gap-1 items-center backdrop-blur-md bg-black/30 text-white">
              <Star size={14} className="fill-yellow-400 text-yellow-400" />
              {course.rating}
            </Badge>
          </div>
        </div>
        
        <CardHeader className="p-4">
          <div className="flex items-center gap-2">
            <Badge variant="outline" className="capitalize">
              {course.category}
            </Badge>
          </div>
          <CardTitle className="text-lg font-semibold mt-2 line-clamp-2">
            {course.title}
          </CardTitle>
          <CardDescription className="line-clamp-2 text-sm">
            {course.description}
          </CardDescription>
        </CardHeader>
        
        <CardContent className="p-4 pt-0">
          <div className="flex items-center gap-2">
            <img
              src={course.instructor.avatar}
              alt={course.instructor.name}
              className="w-6 h-6 rounded-full"
            />
            <span className="text-sm text-muted-foreground">
              {course.instructor.name}
            </span>
          </div>
        </CardContent>
        
        <CardFooter className="p-4 pt-0 flex justify-between text-sm">
          <div className="flex items-center gap-1 text-muted-foreground">
            <Clock size={14} />
            <span>{totalHours} hours</span>
          </div>
          <div className="flex items-center gap-1 text-muted-foreground">
            <Users size={14} />
            <span>{course.enrolledStudents}</span>
          </div>
          <span className="text-muted-foreground">
            {formattedDate}
          </span>
        </CardFooter>
      </Card>
    </Link>
  );
}
