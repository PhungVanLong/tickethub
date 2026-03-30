package management_service.management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventStaffId implements Serializable {
    
    private Long eventId;
    private Long staffId;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventStaffId that = (EventStaffId) o;
        return Objects.equals(eventId, that.eventId) && 
               Objects.equals(staffId, that.staffId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(eventId, staffId);
    }
}
