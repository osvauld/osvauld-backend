import com.shadowsafe.secretsmanagerbackend.secret.dto.SecretByUrlResponseDTO
import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class SecretByUrlResponseDTOList(
    var secrets: List<SecretByUrlResponseDTO> = arrayListOf(),
) : IDTO
