package com.senla.payment.dto.clients;

import com.senla.authorization.dto.UserDataDto;
import com.senla.car.dto.CarDto;
import com.senla.rental.dto.RequestDto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcceptPaymentDto {

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserDataDto userDataDto;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CarDto carDto;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RequestDto requestDto;

}
