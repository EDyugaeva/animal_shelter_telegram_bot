package pro.sky.animal_shelter_telegram_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.animal_shelter_telegram_bot.model.Report;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    void deleteById(Long id);

    Report findReportById(Long id);

    Optional<Report> findReportByDateOfReportAndPetOwner_ChatId(String dateOfReport, Long chatId);

    @Query(value = "SELECT * FROM report WHERE is_report_checked = false", nativeQuery = true)
    Collection<Report> getUncheckedReports();


}