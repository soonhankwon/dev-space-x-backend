package soon.devspacexbackend.category.domain;

import lombok.NoArgsConstructor;
import soon.devspacexbackend.category.presentation.dto.CategoryGetResDto;
import soon.devspacexbackend.category.presentation.dto.CategoryRegisterReqDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Category(CategoryRegisterReqDto dto) {
        this.name = dto.getName().toUpperCase();
    }

    public String getName() {
        return name;
    }

    public CategoryGetResDto convertCategoryGetResDto() {
        return new CategoryGetResDto(this.id, this.name);
    }

}