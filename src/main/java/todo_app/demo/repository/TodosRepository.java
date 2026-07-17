package todo_app.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import todo_app.demo.model.Todos;

public interface TodosRepository extends JpaRepository<Todos, Long> {
      List<Todos> findByUser_Id(Long userId);
}