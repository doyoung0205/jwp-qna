package qna.domain.deletehistory;

import qna.UnAuthorizedException;
import qna.domain.BaseTimeEntity;
import qna.domain.user.User;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@AttributeOverride(name = "createdAt", column = @Column(name = "createDate"))
public class DeleteHistory extends BaseTimeEntity {

    @EmbeddedId
    private DeleteHistoryId deleteHistoryId;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_by_id", foreignKey = @ForeignKey(name = "fk_delete_history_to_user"))
    private User deletedByUser;

    protected DeleteHistory() {
    }

    public DeleteHistory(ContentType contentType, Long contentId, User deletedByUser) {
        validate(deletedByUser);
        this.deleteHistoryId = DeleteHistoryId.of(contentType, contentId);
        this.deletedByUser = deletedByUser;
    }

    private void validate(User deletedByUser) {
        if (Objects.isNull(deletedByUser)) {
            throw new UnAuthorizedException();
        }
    }

    public static DeleteHistory ofQuestion(Long contentId, User writer) {
        return new DeleteHistory(ContentType.QUESTION, contentId, writer);
    }

    public static DeleteHistory ofAnswer(Long contentId, User writer) {
        return new DeleteHistory(ContentType.ANSWER, contentId, writer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeleteHistory that = (DeleteHistory) o;

        return deleteHistoryId != null ? deleteHistoryId.equals(that.deleteHistoryId) : that.deleteHistoryId == null;
    }

    @Override
    public int hashCode() {
        return deleteHistoryId != null ? deleteHistoryId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "DeleteHistory{" +
                "deleteHistoryId=" + deleteHistoryId +
                ", deletedByUser=" + deletedByUser +
                ", createDate=" + getCreatedAt() +
                '}';
    }

    public boolean matchContentId(Long id) {
        return this.deleteHistoryId.matchContentId(id);
    }

    public boolean matchContentType(ContentType contentType) {
        return this.deleteHistoryId.matchContentType(contentType);
    }

    public boolean matchDeletedUser(User deletedByUser) {
        return this.deletedByUser.equals(deletedByUser);
    }

    public boolean isQuestionType() {
        return this.deleteHistoryId.isQuestionType();
    }

    public boolean isAnswerType() {
        return this.deleteHistoryId.isAnswerType();
    }
}
