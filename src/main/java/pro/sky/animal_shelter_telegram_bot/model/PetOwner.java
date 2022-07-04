package pro.sky.animal_shelter_telegram_bot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pro.sky.animal_shelter_telegram_bot.model.pets.Pet;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;


@Entity
public class PetOwner {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private Long chatId;
    private String phoneNumber;
    private Integer dayOfProbation;

    @OneToMany(mappedBy = "ownerOfPet")
    @JsonIgnore
    private Set<Pet> setOfAdoptedPets;

    @OneToMany(mappedBy = "petOwner")
    @JsonIgnore
    private Set<Report> reports;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getDayOfProbation() {
        return dayOfProbation;
    }

    public void setDayOfProbation(Integer dayOfProbation) {
        this.dayOfProbation = dayOfProbation;
    }

    public Set<Pet> getSetOfAdoptedPets() {
        return setOfAdoptedPets;
    }

    public void setSetOfAdoptedPets(Set<Pet> listOfAdoptedPets) {
        this.setOfAdoptedPets = listOfAdoptedPets;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetOwner petOwner = (PetOwner) o;
        return Objects.equals(id, petOwner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PetOwner{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", chatId=" + chatId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dayOfProbation=" + dayOfProbation +
                ", listOfAdoptedPets=" + setOfAdoptedPets +
                ", reports=" + reports +
                '}';
    }
}
