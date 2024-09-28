package com.JavaApiRest.todosimple.repositories;

import com.JavaApiRest.todosimple.models.Task;
import com.JavaApiRest.todosimple.models.projections.TaskProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
        List<TaskProjection> findByUser_Id(Long id);

//    @Query(value = "select t from Task t where t.user.id = :id")
//    List<Task> findByUser_Id(@Param("id") Long id);

    // @Query(value = "SELECT * FROM task t WHERE t.user_id = :id", nativeQuery =
    // true)
    // List<Task> findByUser_Id(@Param("id") Long id);
}
