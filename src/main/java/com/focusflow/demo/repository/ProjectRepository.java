package com.focusflow.demo.repository;

import com.focusflow.demo.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,String>
{
    List<Project> findByUser_Id(String userId);
}
