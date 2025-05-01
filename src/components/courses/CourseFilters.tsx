
import { categories, tags, instructors } from "@/lib/data";
import { useState } from "react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Accordion, AccordionContent, AccordionItem, AccordionTrigger } from "@/components/ui/accordion";
import { Checkbox } from "@/components/ui/checkbox";
import { Search, Filter, X } from "lucide-react";

interface FilterOptions {
  search: string;
  instructorId?: string;
  category?: string;
  tags: string[];
  sortBy?: 'newest' | 'popular' | 'rating';
}

interface CourseFiltersProps {
  onFilterChange: (filters: FilterOptions) => void;
  className?: string;
}

export function CourseFilters({ onFilterChange, className = "" }: CourseFiltersProps) {
  const [filters, setFilters] = useState<FilterOptions>({
    search: "",
    tags: [],
  });
  
  const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newFilters = { ...filters, search: e.target.value };
    setFilters(newFilters);
    onFilterChange(newFilters);
  };
  
  const handleCategoryChange = (category: string) => {
    const newFilters = {
      ...filters,
      category: filters.category === category ? undefined : category,
    };
    setFilters(newFilters);
    onFilterChange(newFilters);
  };
  
  const handleInstructorChange = (instructorId: string) => {
    const newFilters = {
      ...filters,
      instructorId: filters.instructorId === instructorId ? undefined : instructorId,
    };
    setFilters(newFilters);
    onFilterChange(newFilters);
  };
  
  const handleTagChange = (tag: string) => {
    const newFilters = {
      ...filters,
      tags: filters.tags.includes(tag)
        ? filters.tags.filter((t) => t !== tag)
        : [...filters.tags, tag],
    };
    setFilters(newFilters);
    onFilterChange(newFilters);
  };
  
  const handleSortChange = (sortBy: 'newest' | 'popular' | 'rating') => {
    const newFilters = {
      ...filters,
      sortBy: filters.sortBy === sortBy ? undefined : sortBy,
    };
    setFilters(newFilters);
    onFilterChange(newFilters);
  };
  
  const resetFilters = () => {
    const newFilters = {
      search: "",
      tags: [],
    };
    setFilters(newFilters);
    onFilterChange(newFilters);
  };
  
  // Count active filters (excluding search)
  const activeFiltersCount =
    (filters.category ? 1 : 0) +
    (filters.instructorId ? 1 : 0) +
    filters.tags.length +
    (filters.sortBy ? 1 : 0);
  
  return (
    <div className={`space-y-6 ${className}`}>
      <div className="relative">
        <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-muted-foreground" />
        <Input
          placeholder="Search courses..."
          className="pl-8"
          value={filters.search}
          onChange={handleSearchChange}
        />
      </div>
      
      <div className="flex items-center justify-between">
        <div className="flex items-center">
          <Filter size={16} className="mr-2" />
          <span className="text-sm font-medium">Filters</span>
          {activeFiltersCount > 0 && (
            <span className="ml-2 rounded-full bg-primary/10 px-2 py-0.5 text-xs font-medium text-primary">
              {activeFiltersCount}
            </span>
          )}
        </div>
        
        {activeFiltersCount > 0 && (
          <Button variant="ghost" size="sm" onClick={resetFilters} className="h-8 px-2 text-xs">
            <X size={14} className="mr-1" />
            Clear
          </Button>
        )}
      </div>
      
      <Accordion type="multiple" className="w-full">
        <AccordionItem value="categories">
          <AccordionTrigger className="text-sm py-2">Categories</AccordionTrigger>
          <AccordionContent>
            <div className="space-y-2">
              {categories.slice(0, 8).map((category) => (
                <div key={category} className="flex items-center space-x-2">
                  <Checkbox
                    id={`category-${category}`}
                    checked={filters.category === category}
                    onCheckedChange={() => handleCategoryChange(category)}
                  />
                  <label
                    htmlFor={`category-${category}`}
                    className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                  >
                    {category}
                  </label>
                </div>
              ))}
            </div>
          </AccordionContent>
        </AccordionItem>
        
        <AccordionItem value="instructors">
          <AccordionTrigger className="text-sm py-2">Instructors</AccordionTrigger>
          <AccordionContent>
            <div className="space-y-2">
              {instructors.map((instructor) => (
                <div key={instructor.id} className="flex items-center space-x-2">
                  <Checkbox
                    id={`instructor-${instructor.id}`}
                    checked={filters.instructorId === instructor.id}
                    onCheckedChange={() => handleInstructorChange(instructor.id)}
                  />
                  <label
                    htmlFor={`instructor-${instructor.id}`}
                    className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                  >
                    {instructor.name}
                  </label>
                </div>
              ))}
            </div>
          </AccordionContent>
        </AccordionItem>
        
        <AccordionItem value="tags">
          <AccordionTrigger className="text-sm py-2">Tags</AccordionTrigger>
          <AccordionContent>
            <div className="space-y-2">
              {tags.slice(0, 12).map((tag) => (
                <div key={tag} className="flex items-center space-x-2">
                  <Checkbox
                    id={`tag-${tag}`}
                    checked={filters.tags.includes(tag)}
                    onCheckedChange={() => handleTagChange(tag)}
                  />
                  <label
                    htmlFor={`tag-${tag}`}
                    className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                  >
                    {tag}
                  </label>
                </div>
              ))}
            </div>
          </AccordionContent>
        </AccordionItem>
        
        <AccordionItem value="sort-by">
          <AccordionTrigger className="text-sm py-2">Sort By</AccordionTrigger>
          <AccordionContent>
            <div className="space-y-2">
              {[
                { value: 'newest', label: 'Newest' },
                { value: 'popular', label: 'Most Popular' },
                { value: 'rating', label: 'Highest Rated' },
              ].map((option) => (
                <div key={option.value} className="flex items-center space-x-2">
                  <Checkbox
                    id={`sort-${option.value}`}
                    checked={filters.sortBy === option.value}
                    onCheckedChange={() => 
                      handleSortChange(option.value as 'newest' | 'popular' | 'rating')
                    }
                  />
                  <label
                    htmlFor={`sort-${option.value}`}
                    className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                  >
                    {option.label}
                  </label>
                </div>
              ))}
            </div>
          </AccordionContent>
        </AccordionItem>
      </Accordion>
    </div>
  );
}
