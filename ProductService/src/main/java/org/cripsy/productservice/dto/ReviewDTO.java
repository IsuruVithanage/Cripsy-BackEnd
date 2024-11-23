package org.cripsy.productservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private int productId;
    @NotBlank
    private String user;
    @Min(1)
    @Max(5)
    private int rating;
    private String comment;

    public void setComment(String comment){
        if(comment != null && !comment.trim().isEmpty()){
            this.comment = comment.trim();
        } else {
            this.comment = null;
        }
    }
}

