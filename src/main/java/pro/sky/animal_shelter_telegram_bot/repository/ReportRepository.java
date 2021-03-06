package pro.sky.animal_shelter_telegram_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.animal_shelter_telegram_bot.model.Report;

import java.util.Collection;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    void deleteById(Long id);

    Report findReportById(Long id);

}