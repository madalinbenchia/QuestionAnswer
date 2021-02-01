<template>
  <div>
    <div style="margin-top:10px;">
      <el-tooltip content="This answer is useful" placement="top">
        <i
          style="margin-left: 8px;font-size: 200%; cursor:pointer;"
          class="fas fa-chevron-up"
          @click="UpVote(localAnswer.Id)"
        ></i>
      </el-tooltip>
    </div>
    <h2 v-if="localAnswer.Score > 99" style="margin-left:8px; color:gray">
      {{ localAnswer.Score }}
      <el-tooltip
        v-if="authUser.UserId == question.OwnerUserId"
        content="Mark this answer as correct"
        placement="right"
      >
        <i
          :class="
            localAnswer.Id == question.AcceptedAnswerId ? 'greenClass' : ''
          "
          @click="correctAnswer(localAnswer.Id)"
          style="margin-left:8px; cursor:pointer"
          class="fas fa-check"
          v-bind:id="localAnswer.Id"
        ></i>
      </el-tooltip>

      <el-tooltip
        v-else-if="localAnswer.Id == question.AcceptedAnswerId"
        content="This answer is marked as correct"
        placement="right"
      >
        <i
          style="margin-left:8px; cursor:pointer; color:rgb(79, 230, 79)"
          class="fas fa-check"
        ></i>
      </el-tooltip>
    </h2>
    <h2 v-else-if="localAnswer.Score > 9" style="margin-left:9px; color:gray">
      {{ localAnswer.Score }}
      <el-tooltip
        v-if="authUser.UserId == question.OwnerUserId"
        content="Mark this answer as correct"
        placement="right"
      >
        <i
          :class="
            localAnswer.Id == question.AcceptedAnswerId ? 'greenClass' : ''
          "
          @click="correctAnswer(localAnswer.Id)"
          style="margin-left:8px; cursor:pointer"
          class="fas fa-check"
          v-bind:id="localAnswer.Id"
        ></i>
      </el-tooltip>
      <el-tooltip
        v-else-if="localAnswer.Id == question.AcceptedAnswerId"
        content="This answer is marked as correct"
        placement="right"
      >
        <i
          style="margin-left:8px; cursor:pointer; color: rgb(79, 230, 79)"
          class="fas fa-check"
        ></i>
      </el-tooltip>
    </h2>
    <h2 v-else-if="localAnswer.Score <= 9" style="margin-left:15px;color:gray">
      {{ localAnswer.Score }}
      <el-tooltip
        v-if="authUser.UserId == question.OwnerUserId"
        content="Mark this answer as correct"
        placement="right"
      >
        <i
          :class="
            localAnswer.Id == question.AcceptedAnswerId ? 'greenClass' : ''
          "
          @click="correctAnswer(localAnswer.Id)"
          style="margin-left:8px; cursor:pointer"
          class="fas fa-check"
          v-bind:id="localAnswer.Id"
        ></i>
      </el-tooltip>
      <el-tooltip
        v-else-if="localAnswer.Id == question.AcceptedAnswerId"
        content="This answer is marked as correct"
        placement="right"
      >
        <i
          style="margin-left:8px; cursor:pointer; color:rgb(79, 230, 79)"
          class="fas fa-check"
        ></i>
      </el-tooltip>
    </h2>
    <div style="margin-top:10px;">
      <el-tooltip content="This answer is not useful" placement="bottom">
        <i
          style="margin-left: 8px;font-size: 200%; cursor:pointer;"
          class="fas fa-chevron-down"
          @click="DownVote(localAnswer.Id)"
        ></i>
      </el-tooltip>
    </div>

    <card class="no-border-card" style="margin-left:80px; margin-top:-80px">
      <el-tooltip
        v-if="authUser.UserId == localAnswer.OwnerUserId"
        style="cursor: pointer; float:right;top:-10px; position:relative; font-size:large"
        content="Delete"
        placement="top"
      >
        <i
          id="delq"
          class="far fa-trash-alt"
          @click="deleteAnswer(localAnswer.Id)"
        ></i>
      </el-tooltip>

      <el-tooltip
        v-if="authUser.UserId == localAnswer.OwnerUserId"
        style="cursor: pointer; float:right;top:-10px; position:relative; font-size:large; margin-right:6px"
        content="Edit"
        placement="top"
      >
        <i class="far fa-edit" @click="editAnswer(localAnswer.Id)"></i>
      </el-tooltip>

      <p class="card-text" v-html="localAnswer.Body"></p>
      <p class="" style="font-size:smaller; margin-left:80%; color:gray">
        answered <i>{{ format_date(localAnswer.CreationDate) }}</i> by
        <b style="cursor:pointer" @click="viewUser(answerOwner.UserId)">{{
          answerOwner.DisplayName
        }}</b>
      </p>
      {{ localComments }}
      <div
        v-for="(comment, index) in localAnswer.CommentsList"
        :key="comment.Id"
      >
        <comment
          v-bind:id="comment.Id"
          v-bind:text="comment.Text"
          v-bind:cdate="comment.CreationDate"
          v-bind:uid="comment.UserId"
        ></comment>
      </div>

      <form style="margin-top:30px">
        <textarea
          v-model="comm[index]"
          maxlength="20"
          style="resize: none;"
          class="form-control"
          placeholder="Write your comment"
          rows="1"
          @keydown.enter.exact.prevent
          @keyup.enter.exact="addComment(localAnswer.Id, index)"
          @keydown.enter.shift.exact="newline"
        >
        </textarea>
      </form>
    </card>
  </div>
</template>
<script>
import moment from "moment";
import Comment from "@/components/Feed/Comment";
import swal from "sweetalert2";
import "sweetalert2/dist/sweetalert2.css";

export default {
  name: "qanswer",
  components: {
    Comment
  },
  props: {
    index: {
      type: Number,
      default: ""
    },
    answer: {
      type: Object,
      default: ""
    },
    question: {
      type: Object,
      default: ""
    }
  },

  data() {
    return {
      answerOwner: "",
      authUser: "",
      questionOwner: "",
      comm: [],
      localAnswer: this.answer,
      localComments: ""
    };
  },
  created() {
    this.get();
  },

  methods: {
    async get() {
      await this.$store.dispatch("users/get", this.localAnswer.OwnerUserId);
      this.answerOwner = await { ...this.$store.getters["users/user"] };

      this.authUser = await { ...this.$store.getters.currentUser };

      const questionOwnerId = this.question.OwnerUserId;

      await this.$store.dispatch("users/get", questionOwnerId);
      this.questionOwner = await { ...this.$store.getters["users/user"] };
    },
    format_date(value) {
      if (value) {
        return moment(String(value)).format("MMM D 'YY, h:mm");
      }
    },
    async viewUser(Id) {
      this.$router.push({
        name: "View User",
        params: { id: Id }
      });
    },

    async correctAnswer(Id) {
      this.question.AcceptedAnswerId = Id;
      this.question.LastEditorDisplayName = this.questionOwner.DisplayName;
      this.question.CloseDate = "2011-01-01T02:11:46.083";
      await this.$store.dispatch("questions/update", this.question);
      var element = document.getElementById(Id);
      element.classList.add("greenClassvu");
    },

    async UpVote(id) {
      try {
        if (this.$store.getters.isAuthenticated == false) {
          this.$notify({
            type: "danger",
            message: "Oops, login to upvote!"
          });
        } else {
          let v = { PostId: id, UserId: this.authUser.UserId, VoteTypeId: "2" };

          await this.$store.dispatch("questions/vote", v);

          await this.$store.dispatch("questions/get", id);
          this.localAnswer = this.$store.getters["questions/question"];
        }
      } catch (error) {
        this.$notify({
          type: "danger",
          message: "Oops, something went wrong!"
        });
      }
    },

    async DownVote(id) {
      if (this.$store.getters.isAuthenticated == false) {
        this.$notify({
          type: "danger",
          message: "Oops, login to downvote!"
        });
      } else {
        try {
          let v = { PostId: id, UserId: this.authUser.UserId, VoteTypeId: "3" };

          await this.$store.dispatch("questions/vote", v);
          await this.$store.dispatch("questions/get", id);
          this.localAnswer = this.$store.getters["questions/question"];
        } catch (error) {
          this.$notify({
            type: "danger",
            message: "Oops, something went wrong!"
          });
        }
      }
    },

    async addComment(id, index) {
      let co = this.comm[index] || "";
      if (co.length == 0) {
        this.$notify({
          type: "danger",
          message: "Oops, comment should not be empty!"
        });
      } else {
        try {
          let c = {
            PostId: id,
            Text: this.comm[index],
            UserId: this.authUser.UserId
          };

          await this.$store.dispatch("questions/comment", c);
          this.comm[index] = "";

          await this.$store.dispatch("comments/get", id);
          this.localComments = await {
            ...this.$store.getters["comments/comment"]
          };
          this.localAnswer.CommentsList = this.localComments;

          this.$notify({
            type: "success",
            message: "Comment added successfully."
          });
        } catch (error) {
          console.log(error);
          this.$notify({
            type: "danger",
            message: "Oops, login first!"
          });
        }
      }
    },
    async deleteAnswer(id) {
      const confirmation = await swal.fire({
        title: `Are you sure?`,
        type: "question",
        buttonsStyling: false,
        showCancelButton: true,
        confirmButtonText: "Yes, delete it!",
        cancelButtonText: "No, keep it",
        confirmButtonClass: "btn btn-primary",
        cancelButtonClass: "btn btn-warning"
      });

      try {
        if (confirmation.value === true) {
          await this.$store.dispatch("answers/destroy", id);
          location.reload();

          this.$notify({
            type: "success",
            message: "Answer deleted successfully."
          });
        }
      } catch (error) {
        console.log(error);
        this.$notify({
          type: "danger",
          message: "Oops, something went wrong!"
        });
      }
    },

    async editAnswer(Id) {
      this.$router.push({
        name: "Edit Answer",
        params: { id: Id }
      });
    }
  }
};
</script>
<style>
.greenClass {
  color: rgb(79, 230, 79);
}
</style>
