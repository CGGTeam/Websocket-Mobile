package cgodin.qc.ca.projet.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(as= SanitizedUser.class)
@JsonDeserialize(as=CompteImpl.class)
public interface SanitizedUser {
    String getCourriel();
    String getAlias();
    Long getAvatarId();
    void setAvatarId(Long id);
    Role getRole();
    Groupe getGroupe();
}
