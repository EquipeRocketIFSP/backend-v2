package br.vet.certvet.dto.responses;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RequiredArgsConstructor(staticName = "of")
public class TokenResponseDto {
    @NonNull
    private String token;
    @NonNull
    private String type;
    private String nome;
}
