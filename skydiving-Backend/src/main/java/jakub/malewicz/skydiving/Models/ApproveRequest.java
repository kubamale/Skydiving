package jakub.malewicz.skydiving.Models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "approval_request")
@NoArgsConstructor
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

}
