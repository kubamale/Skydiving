package jakub.malewicz.skydiving.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "approval_request")
@NoArgsConstructor
@AllArgsConstructor
public class ApproveRequest {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "approver_id")
    private Skydiver approver;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private Skydiver requester;

    public ApproveRequest(Skydiver approver, Skydiver requester) {
        this.approver = approver;
        this.requester = requester;
    }
}
