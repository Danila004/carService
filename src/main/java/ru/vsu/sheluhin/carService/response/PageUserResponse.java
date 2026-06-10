package ru.vsu.sheluhin.carService.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageUserResponse {
    private List<UserResponse> users;
    private Integer totalPages;
    private Integer pageNumber;

    public static PageUserResponse from(Page<UserResponse> page) {
        PageUserResponse response = new PageUserResponse();

        response.setUsers(page.getContent());
        response.setPageNumber(page.getNumber());
        response.setTotalPages(page.getTotalPages());

        return response;
    }
}
