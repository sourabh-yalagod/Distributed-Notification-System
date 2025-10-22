package notification_api.models.entity;

import java.time.LocalDateTime;

public abstract class AbstractModel {
    LocalDateTime createdAt;
    LocalDateTime updateAt;

    public void getCreatedAt() {
        this.createdAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    public void getUpdateAt() {
        this.updateAt = LocalDateTime.now();
    }
}
