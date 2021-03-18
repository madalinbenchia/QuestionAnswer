<template>
  <div>
    <div style="margin-top:10px;">
      <el-tooltip content="This answer is useful" placement="top">
        <i
          style="margin-left: 8px;font-size: 200%; cursor:pointer;"
          class="fas fa-chevron-up"
          @click="UpVote(localAnswer.id)"
        ></i>
      </el-tooltip>
    </div>
    <h2 v-if="localAnswer.score > 99" style="margin-left:8px; color:gray">
      {{ localAnswer.score }}
      <el-tooltip
        v-if="authUser.userId == question.ownerUserId"
        content="Mark this answer as correct"
        placement="right"
      >
        <i
          :class="
            localAnswer.id == question.acceptedAnswerId ? 'greenClass' : ''
          "
          @click="correctAnswer(localAnswer.id)"
          style="margin-left:8px; cursor:pointer"
          class="fas fa-check"
          v-bind:id="localAnswer.id"
        ></i>
      </el-tooltip>

      <el-tooltip
        v-else-if="localAnswer.id == question.acceptedAnswerId"
        content="This answer is marked as correct"
        placement="right"
      >
        <i
          style="margin-left:8px; cursor:pointer; color:rgb(79, 230, 79)"
          class="fas fa-check"
        ></i>
      </el-tooltip>
    </h2>
    <h2 v-else-if="localAnswer.score > 9" style="margin-left:9px; color:gray">
      {{ localAnswer.score }}
      <el-tooltip
        v-if="authUser.userId == question.ownerUserId"
        content="Mark this answer as correct"
        placement="right"
      >
        <i
          :class="
            localAnswer.id == question.acceptedAnswerId ? 'greenClass' : ''
          "
          @click="correctAnswer(localAnswer.id)"
          style="margin-left:8px; cursor:pointer"
          class="fas fa-check"
          v-bind:id="localAnswer.id"
        ></i>
      </el-tooltip>
      <el-tooltip
        v-else-if="localAnswer.id == question.acceptedAnswerId"
        content="This answer is marked as correct"
        placement="right"
      >
        <i
          style="margin-left:8px; cursor:pointer; color: rgb(79, 230, 79)"
          class="fas fa-check"
        ></i>
      </el-tooltip>
    </h2>
    <h2 v-else-if="localAnswer.score <= 9" style="margin-left:15px;color:gray">
      {{ localAnswer.score }}
      <el-tooltip
        v-if="authUser.userId == question.ownerUserId"
        content="Mark this answer as correct"
        placement="right"
      >
        <i
          :class="
            localAnswer.id == question.acceptedAnswerId ? 'greenClass' : ''
          "
          @click="correctAnswer(localAnswer.id)"
          style="margin-left:8px; cursor:pointer"
          class="fas fa-check"
          v-bind:id="localAnswer.id"
        ></i>
      </el-tooltip>
      <el-tooltip
        v-else-if="localAnswer.id == question.acceptedAnswerId"
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
          @click="DownVote(localAnswer.id)"
        ></i>
      </el-tooltip>
    </div>

    <card class="no-border-card" style="margin-left:80px; margin-top:-80px">
      <el-tooltip
        v-if="authUser.userId == localAnswer.ownerUserId"
        style="cursor: pointer; float:right;top:-10px; position:relative; font-size:large"
        content="Delete"
        placement="top"
      >
        <i
          id="delq"
          class="far fa-trash-alt"
          @click="deleteAnswer(localAnswer.id)"
        ></i>
      </el-tooltip>

      <el-tooltip
        v-if="authUser.userId == localAnswer.ownerUserId"
        style="cursor: pointer; float:right;top:-10px; position:relative; font-size:large; margin-right:6px"
        content="Edit"
        placement="top"
      >
        <i class="far fa-edit" @click="editAnswer(localAnswer.id)"></i>
      </el-tooltip>

      <p class="card-text" v-html="localAnswer.body"></p>
      <p class="" style="font-size:smaller; margin-left:80%; color:gray">
        answered <i>{{ format_date(localAnswer.creationDate) }}</i> by
        <b style="cursor:pointer" @click="viewUser(answerOwner.userId)">{{
          answerOwner.displayName
        }}</b>
      </p>
      <div v-for="(comment, index) in localAnswer.comments" :key="comment.id">
        <comment
          v-bind:id="comment.postId"
          v-bind:text="comment.text"
          v-bind:cdate="comment.creationDate"
          v-bind:uid="comment.userId"
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
          @keyup.enter.exact="addComment(localAnswer.id, index)"
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
      localComments: "",
      oldFav: -1
    };
  },
  created() {
    this.get();
  },

  methods: {
    async get() {
      await this.$store.dispatch("users/get", this.localAnswer.ownerUserId);
      this.answerOwner = await { ...this.$store.getters["users/user"] };

      this.authUser = await { ...this.$store.getters.currentUser };

      const questionOwnerId = this.question.ownerUserId;

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

    async correctAnswer(id) {
      this.oldFav = document.getElementsByClassName("greenClass");
      if (this.oldFav[0]) {
        this.oldFav[0].classList.remove("greenClass");
      }
      const payload = {
        questionId: this.question.id,
        answerId: id
      };
      await this.$store.dispatch("questions/markAsCorrect", payload);
      var element = document.getElementById(id);
      element.classList.add("greenClass");
    },

    async UpVote(id) {
      try {
        if (this.$store.getters.currentUser == null) {
          this.$notify({
            type: "danger",
            message: "Oops, login to upvote!"
          });
        } else {
          let v = { postId: id, userId: this.authUser.userId, voteTypeId: "2" };

          await this.$store.dispatch("questions/vote", v);

          await this.$store.dispatch("answers/get", id);
          this.localAnswer = this.$store.getters["answers/answer"];
        }
      } catch (error) {
        this.$notify({
          type: "danger",
          message: "Oops, something went wrong!"
        });
      }
    },

    async DownVote(id) {
      if (this.$store.getters.currentUser == null) {
        this.$notify({
          type: "danger",
          message: "Oops, login to downvote!"
        });
      } else {
        try {
          let v = { postId: id, userId: this.authUser.userId, voteTypeId: "3" };

          await this.$store.dispatch("questions/vote", v);
          await this.$store.dispatch("answers/get", id);
          this.localAnswer = this.$store.getters["answers/answer"];
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
            postId: id,
            text: this.comm[index],
            userId: this.authUser.userId
          };

          await this.$store.dispatch("questions/comment", c);
          this.comm[index] = "";

          await this.$store.dispatch("comments/get", id);
          this.localComments = await {
            ...this.$store.getters["comments/comment"]
          };
          this.localAnswer.comments = this.localComments;

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
