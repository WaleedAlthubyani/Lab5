package com.example.projecttrackersystem.Controller;

import com.example.projecttrackersystem.ApiResponse.ApiResponse;
import com.example.projecttrackersystem.Model.Project;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/project-tracker-system")
public class ProjectTrackerSystemController {
    ArrayList<Project> projects = new ArrayList<>();

    @GetMapping("/projects")
    public ArrayList<Project> displayProjects() {
        return projects;
    }

    @PostMapping("/create-new-project")
    public ApiResponse createNewProject(@RequestBody Project project) {
        for (Project value : projects) {
            if (project.getID().equals(value.getID())) {
                return new ApiResponse("A project with this ID already exists");
            }
        }

        projects.add(project);
        return new ApiResponse("New project created successfully");
    }

    @PutMapping("/update-project/{id}")
    public ApiResponse updateProject(@RequestBody Project project,@PathVariable String id) {
        if (!project.getID().equals(id)) {
            for (Project value : projects) {
                if (project.getID().equals(value.getID())) {
                    return new ApiResponse("A project with this ID already exists");
                }
            }
        }

        for (int i = 0; i < projects.size(); i++) {
            if (project.getID().equals(projects.get(i).getID())) {
                projects.set(i, project);
                return new ApiResponse("Project updated successfully");
            }
        }
        return new ApiResponse("Project not found");
    }

    @DeleteMapping("/delete-project/{id}")
    public ApiResponse deleteProject(@PathVariable String id) {
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getID().equals(id)) {
                projects.remove(i);
                return new ApiResponse("Project deleted successfully");
            }
        }
        return new ApiResponse("Project not found");
    }

    @PutMapping("/change-status/{id}/{status}")
    public ApiResponse changeStatus(@PathVariable String id,@PathVariable String status) {
        if (!(status.equals("done")||status.equals("not done"))) {
            return new ApiResponse("Status can only be done or not done");
        }

        for (Project project : projects) {
            if (project.getID().equals(id)) {
                project.setStatus(status);
                return new ApiResponse("Project status changed successfully");
            }
        }
        return new ApiResponse("Project not found");
    }

    @GetMapping("projects-by-company/{companyName}")
    public ArrayList<Project> getProjectsByCompanyName(@PathVariable String companyName) {
        ArrayList<Project> projectsByCompany = new ArrayList<>();
        for (Project project : projects) {
            if (project.getCompanyName().equals(companyName)) {
                projectsByCompany.add(project);
            }
        }
        return projectsByCompany;
    }
}
