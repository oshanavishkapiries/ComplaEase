
import { Layout } from "@/components/layout/Layout";
import { VideoPlayer } from "@/components/courses/VideoPlayer";
import { useCourses } from "@/contexts/CoursesContext";
import { useParams, Link } from "react-router-dom";
import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import {
  BookOpen,
  Clock,
  Calendar,
  CheckCircle2,
  PlayCircle,
  ChevronDown,
  Star,
  User,
} from "lucide-react";
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";
import {
  Tabs,
  TabsContent,
  TabsList,
  TabsTrigger,
} from "@/components/ui/tabs";
import { ScrollArea } from "@/components/ui/scroll-area";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Separator } from "@/components/ui/separator";
import { Textarea } from "@/components/ui/textarea";
import { Lecture, comments } from "@/lib/data";

const CourseDetailPage = () => {
  const { id } = useParams<{ id: string }>();
  const { getCourseById } = useCourses();
  const course = getCourseById(id || "");
  const [selectedLecture, setSelectedLecture] = useState<Lecture | null>(
    course?.lectures[0] || null
  );
  const [activeTab, setActiveTab] = useState("overview");
  
  // Format date to a readable format
  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString("en-US", {
      year: "numeric",
      month: "long",
      day: "numeric",
    });
  };
  
  // Calculate total course duration
  const calculateTotalDuration = () => {
    if (!course) return "0h 0m";
    
    const totalSeconds = course.lectures.reduce(
      (total, lecture) => total + lecture.duration,
      0
    );
    
    const hours = Math.floor(totalSeconds / 3600);
    const minutes = Math.floor((totalSeconds % 3600) / 60);
    
    return `${hours}h ${minutes}m`;
  };
  
  if (!course) {
    return (
      <Layout>
        <div className="container py-16 text-center">
          <h1 className="text-2xl font-bold mb-4">Course Not Found</h1>
          <p className="mb-8">
            The course you're looking for doesn't exist or has been removed.
          </p>
          <Button asChild>
            <Link to="/courses">Back to Courses</Link>
          </Button>
        </div>
      </Layout>
    );
  }
  
  return (
    <Layout>
      <div className="container px-4 py-8">
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Main Content - Video Player and Info */}
          <div className="lg:col-span-2 space-y-8">
            {/* Video Player */}
            <VideoPlayer
              src={selectedLecture?.videoUrl || ""}
              poster={course.thumbnail}
              title={selectedLecture?.title}
              className="w-full aspect-video rounded-lg overflow-hidden"
            />
            
            {/* Course Info Tabs */}
            <Tabs
              defaultValue="overview"
              value={activeTab}
              onValueChange={setActiveTab}
              className="w-full"
            >
              <TabsList className="mb-4">
                <TabsTrigger value="overview">Overview</TabsTrigger>
                <TabsTrigger value="lectures">Lectures</TabsTrigger>
                <TabsTrigger value="discussion">Discussion</TabsTrigger>
              </TabsList>
              
              <TabsContent value="overview" className="space-y-6">
                <div>
                  <h1 className="text-2xl font-bold">{course.title}</h1>
                  <div className="flex flex-wrap items-center gap-2 mt-2">
                    <Badge variant="outline">{course.category}</Badge>
                    <div className="flex items-center gap-1 text-sm text-muted-foreground">
                      <Calendar size={14} />
                      <span>Updated {formatDate(course.updatedAt)}</span>
                    </div>
                    <div className="flex items-center gap-1 text-sm text-muted-foreground">
                      <Star size={14} className="fill-yellow-400 text-yellow-400" />
                      <span>{course.rating} rating</span>
                    </div>
                    <div className="flex items-center gap-1 text-sm text-muted-foreground">
                      <User size={14} />
                      <span>{course.enrolledStudents} students</span>
                    </div>
                  </div>
                </div>
                
                <div>
                  <h2 className="text-xl font-semibold mb-2">About This Course</h2>
                  <p className="text-muted-foreground">{course.description}</p>
                </div>
                
                <div>
                  <h2 className="text-xl font-semibold mb-2">What You'll Learn</h2>
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-2">
                    {[
                      "Understand key concepts in the field",
                      "Apply theoretical knowledge to practical scenarios",
                      "Analyze complex problems using proven methodologies",
                      "Create effective solutions based on academic research",
                      "Evaluate and critique existing approaches",
                      "Develop professional skills relevant to the industry",
                    ].map((item, index) => (
                      <div key={index} className="flex items-start gap-2">
                        <CheckCircle2 size={18} className="text-green-500 mt-0.5" />
                        <span>{item}</span>
                      </div>
                    ))}
                  </div>
                </div>
                
                <div>
                  <h2 className="text-xl font-semibold mb-2">Topics Covered</h2>
                  <div className="flex flex-wrap gap-2">
                    {course.tags.map((tag) => (
                      <Badge key={tag} variant="secondary">
                        {tag}
                      </Badge>
                    ))}
                  </div>
                </div>
                
                <div>
                  <h2 className="text-xl font-semibold mb-2">Instructor</h2>
                  <div className="flex items-center gap-4">
                    <Avatar className="h-12 w-12">
                      <AvatarImage src={course.instructor.avatar} alt={course.instructor.name} />
                      <AvatarFallback>
                        {course.instructor.name.charAt(0)}
                      </AvatarFallback>
                    </Avatar>
                    <div>
                      <h3 className="font-medium">{course.instructor.name}</h3>
                      <p className="text-sm text-muted-foreground">
                        Professor, Department of {course.category}
                      </p>
                    </div>
                  </div>
                </div>
              </TabsContent>
              
              <TabsContent value="lectures" className="space-y-4">
                <h2 className="text-xl font-semibold mb-2">Course Content</h2>
                <div className="flex items-center justify-between mb-2">
                  <div className="text-muted-foreground text-sm">
                    {course.lectures.length} lectures • {calculateTotalDuration()} total length
                  </div>
                </div>
                
                <Accordion type="single" collapsible className="w-full">
                  <AccordionItem value="section-1">
                    <AccordionTrigger className="font-medium">
                      Section 1: Introduction
                    </AccordionTrigger>
                    <AccordionContent>
                      {course.lectures.slice(0, Math.max(1, Math.floor(course.lectures.length / 2))).map((lecture) => (
                        <div
                          key={lecture.id}
                          onClick={() => setSelectedLecture(lecture)}
                          className={`flex items-center justify-between p-2 rounded-md cursor-pointer hover:bg-muted/50 ${
                            selectedLecture?.id === lecture.id
                              ? "bg-muted font-medium"
                              : ""
                          }`}
                        >
                          <div className="flex items-center gap-2">
                            <PlayCircle size={18} className={selectedLecture?.id === lecture.id ? "text-primary" : ""} />
                            <span>{lecture.title}</span>
                          </div>
                          <span className="text-sm text-muted-foreground">
                            {Math.floor(lecture.duration / 60)}m
                          </span>
                        </div>
                      ))}
                    </AccordionContent>
                  </AccordionItem>
                  
                  {course.lectures.length > 1 && (
                    <AccordionItem value="section-2">
                      <AccordionTrigger className="font-medium">
                        Section 2: Advanced Concepts
                      </AccordionTrigger>
                      <AccordionContent>
                        {course.lectures.slice(Math.floor(course.lectures.length / 2)).map((lecture) => (
                          <div
                            key={lecture.id}
                            onClick={() => setSelectedLecture(lecture)}
                            className={`flex items-center justify-between p-2 rounded-md cursor-pointer hover:bg-muted/50 ${
                              selectedLecture?.id === lecture.id
                                ? "bg-muted font-medium"
                                : ""
                            }`}
                          >
                            <div className="flex items-center gap-2">
                              <PlayCircle size={18} className={selectedLecture?.id === lecture.id ? "text-primary" : ""} />
                              <span>{lecture.title}</span>
                            </div>
                            <span className="text-sm text-muted-foreground">
                              {Math.floor(lecture.duration / 60)}m
                            </span>
                          </div>
                        ))}
                      </AccordionContent>
                    </AccordionItem>
                  )}
                </Accordion>
              </TabsContent>
              
              <TabsContent value="discussion" className="space-y-6">
                <h2 className="text-xl font-semibold mb-2">Discussion</h2>
                
                <div className="space-y-4">
                  <Textarea placeholder="Add your comment..." className="min-h-[100px]" />
                  <Button className="btn-gradient">Post Comment</Button>
                </div>
                
                <Separator className="my-6" />
                
                <div className="space-y-6">
                  {comments.map((comment) => (
                    <div key={comment.id} className="space-y-4">
                      <div className="flex items-start gap-4">
                        <Avatar>
                          <AvatarImage src={comment.user.avatar} alt={comment.user.name} />
                          <AvatarFallback>
                            {comment.user.name.charAt(0)}
                          </AvatarFallback>
                        </Avatar>
                        <div className="flex-1">
                          <div className="flex items-center justify-between">
                            <h4 className="font-medium">{comment.user.name}</h4>
                            <span className="text-xs text-muted-foreground">
                              {new Date(comment.createdAt).toLocaleDateString()}
                            </span>
                          </div>
                          <p className="mt-1">{comment.content}</p>
                          <Button variant="ghost" size="sm" className="mt-2 h-7 px-2 text-xs">
                            Reply
                          </Button>
                        </div>
                      </div>
                      
                      {/* Replies */}
                      {comment.replies && comment.replies.length > 0 && (
                        <div className="pl-12 space-y-4">
                          {comment.replies.map((reply) => (
                            <div key={reply.id} className="flex items-start gap-4">
                              <Avatar className="h-8 w-8">
                                <AvatarImage src={reply.user.avatar} alt={reply.user.name} />
                                <AvatarFallback>
                                  {reply.user.name.charAt(0)}
                                </AvatarFallback>
                              </Avatar>
                              <div className="flex-1">
                                <div className="flex items-center justify-between">
                                  <h4 className="font-medium text-sm">
                                    {reply.user.name}
                                  </h4>
                                  <span className="text-xs text-muted-foreground">
                                    {new Date(reply.createdAt).toLocaleDateString()}
                                  </span>
                                </div>
                                <p className="mt-1 text-sm">{reply.content}</p>
                                <Button
                                  variant="ghost"
                                  size="sm"
                                  className="mt-1 h-7 px-2 text-xs"
                                >
                                  Reply
                                </Button>
                              </div>
                            </div>
                          ))}
                        </div>
                      )}
                    </div>
                  ))}
                </div>
              </TabsContent>
            </Tabs>
          </div>
          
          {/* Sidebar */}
          <div className="space-y-6">
            {/* Course Stats Card */}
            <div className="p-6 border rounded-lg bg-card">
              <h2 className="text-xl font-semibold mb-4">Course Information</h2>
              <div className="space-y-4">
                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-2">
                    <BookOpen size={16} />
                    <span>Lectures</span>
                  </div>
                  <span>{course.lectures.length}</span>
                </div>
                
                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-2">
                    <Clock size={16} />
                    <span>Total Duration</span>
                  </div>
                  <span>{calculateTotalDuration()}</span>
                </div>
                
                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-2">
                    <Calendar size={16} />
                    <span>Last Updated</span>
                  </div>
                  <span>{formatDate(course.updatedAt)}</span>
                </div>
                
                <Button className="w-full btn-gradient">Enroll Now</Button>
              </div>
            </div>
            
            {/* Lecture List */}
            <div className="border rounded-lg bg-card overflow-hidden">
              <div className="p-4 border-b">
                <h2 className="font-semibold">Lecture List</h2>
              </div>
              <ScrollArea className="h-[400px]">
                <div className="p-4 space-y-1">
                  {course.lectures.map((lecture) => (
                    <div
                      key={lecture.id}
                      onClick={() => setSelectedLecture(lecture)}
                      className={`flex items-center gap-2 p-2 rounded-md cursor-pointer ${
                        selectedLecture?.id === lecture.id
                          ? "bg-primary/10 text-primary"
                          : "hover:bg-muted/50"
                      }`}
                    >
                      <PlayCircle size={16} />
                      <span className="text-sm line-clamp-1">{lecture.title}</span>
                    </div>
                  ))}
                </div>
              </ScrollArea>
            </div>
            
            {/* Tags */}
            <div className="border rounded-lg bg-card p-4">
              <h2 className="font-semibold mb-2">Tags</h2>
              <div className="flex flex-wrap gap-2">
                {course.tags.map((tag) => (
                  <Badge key={tag} variant="outline">
                    {tag}
                  </Badge>
                ))}
              </div>
            </div>
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default CourseDetailPage;
