package com.example.model.question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResolve {
    private int easyResolve;
    private int meddleResolve;
    private int hardResolve;
    private int nightmareResolve;
}
