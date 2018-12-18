package purchases.application.purchasescollection.infrastructure.model.dto;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class UserDto {

    private String id;
    private String name;
    private String email;
    private Task<GetTokenResult> tokenResultTask;

    public UserDto(String id, String name, String email, Task<GetTokenResult> tokenResultTask) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.tokenResultTask = tokenResultTask;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Task<GetTokenResult> getTokenResultTask() {
        return tokenResultTask;
    }

    public static UserDto map(FirebaseUser firebaseUser){

        return new UserDto(
                firebaseUser.getUid(),
                firebaseUser.getDisplayName(),
                firebaseUser.getEmail(),
                firebaseUser.getIdToken(false)
        );
    }
}
