package pro.sky.animal_shelter_telegram_bot.model.pets;

import pro.sky.animal_shelter_telegram_bot.model.Report;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class PhotoOfPet {

    @Id
    @GeneratedValue
    private Long id;

    private String filePath;
    private long fileSize;
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;

    public PhotoOfPet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }



    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoOfPet that = (PhotoOfPet) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PhotoOfPet{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", report=" + report +
                '}';
    }
}
