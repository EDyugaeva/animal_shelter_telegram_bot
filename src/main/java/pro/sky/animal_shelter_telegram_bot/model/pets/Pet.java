package pro.sky.animal_shelter_telegram_bot.model.pets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.model.Report;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Objects;

@Entity
public abstract class Pet {

    @Id
    @GeneratedValue
    private Long id;

    private String nameOfPet;
    private String health;
    private String extraInfoOfPet;

    @ManyToOne
    private PetOwner ownerOfPet;

    @OneToMany
    @JsonIgnore
    private ArrayList<Report> reports;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfPet() {
        return nameOfPet;
    }

    public void setNameOfPet(String nameOfPet) {
        this.nameOfPet = nameOfPet;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getExtraInfoOfPet() {
        return extraInfoOfPet;
    }

    public void setExtraInfoOfPet(String extraInfoOfPet) {
        this.extraInfoOfPet = extraInfoOfPet;
    }

    public PetOwner getOwnerOfPet() {
        return ownerOfPet;
    }

    public void setOwnerOfPet(PetOwner ownerOfPet) {
        this.ownerOfPet = ownerOfPet;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", nameOfPet='" + nameOfPet + '\'' +
                ", health='" + health + '\'' +
                ", extraInfoOfPet='" + extraInfoOfPet + '\'' +
                ", ownerOfPet=" + ownerOfPet +
                '}';
    }
}
