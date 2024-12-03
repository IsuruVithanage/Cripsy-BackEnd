package org.cripsy.productservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddReviewDTO {
    private int productId;
    private int userId;
    private String userName;
    @Min(1)
    @Max(5)
    private int rating;
    private String comment;

    @SuppressWarnings("unused")
    public void setComment(String comment){
        if(comment != null && !comment.trim().isEmpty()){
            this.comment = comment.trim();
        } else {
            this.comment = null;
        }
    }
}

