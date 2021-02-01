<template>
  <div class="container-fluid">
    <div class="row"> 
      <div class="col-xl-12 order-xl-1">
        <div slot="empty" v-if="loading">
          <img src="/img/loading.gif" style="height: 100px; width: 100px; display: block;
            margin-left: auto;
            margin-right: auto;"/>
        </div>
        <div v-else>
         <div style="margin-top:150px;">
            <el-tooltip content="This question is useful" placement="top">
              <i
                style="cursor:pointer; margin-left: 8px;font-size: 200%"
                class="fas fa-chevron-up"
                @click="UpVote(question.Id)"
              ></i>
              </el-tooltip>
            </div>
            <h1 v-if="question.Score > 99" style="margin-left:8px; color:gray">
              {{ question.Score }}
            </h1>
            <h1
              v-else-if="question.Score > 9"
              style="margin-left:9px; color:gray"
            >
              {{ question.Score }}
            </h1>
            <h1
              v-else-if="question.Score <= 9"
              style="margin-left:15px;color:gray"
            >
              {{ question.Score }}
            </h1>
             <div style="margin-top:10px;">
                <el-tooltip content="This question is not useful" placement="bottom">
              <i
                style="margin-left: 8px;font-size: 200%; cursor:pointer;"
                class="fas fa-chevron-down"
                @click="DownVote(question.Id)"
              ></i>
                </el-tooltip>
            </div>
       

          <card
            class="no-border-card"
            body-classes="px-4 pb-2"
            footer-classes="pb-2"
            style="margin-left:80px ;margin-top:-220px"
          >
            <h2 slot="header" class="h2 mb-0">{{ question.Title }}</h2>

          
            <el-tooltip v-if = "user.UserId==question.OwnerUserId" slot="header"  style="cursor: pointer; float:right;top:-28px; position:relative; font-size:large" content="Delete" placement="top">
              <i id = "delq" class="far fa-trash-alt" @click="deleteQuestion(question.Id)"></i>
            </el-tooltip>

             <el-tooltip  v-if = "user.UserId==question.OwnerUserId" slot="header"  style="cursor: pointer; float:right;top:-28px; position:relative; font-size:large;  margin-right:6px" content="Edit" placement="top">
              <i class="far fa-edit" @click="editQuestion(question.Id)"></i>
            </el-tooltip>
          

            <div
              slot="header"
              style="white-space: nowrap; width: 100px;"
              class="d-flex justify-content-between mt-2"
            >
              <p slot="header" class="mr-4">
                Asked <i>{{ this.askedTimeAgo }}</i> by  <b style="cursor:pointer" @click="viewUser(questionOwner.UserId)">{{this.questionOwner.DisplayName}}</b>
              </p>

               <p v-if="editedTimeAgo != 0"slot="header" class="mr-4">
                Edited <i>{{ this.editedTimeAgo }}</i>
              </p>
            </div>
            
            <p class="card-text mb-4" v-html="question.Body"></p>
            <span
              v-for="tag in question.Tags.split('<')"
              :key="tag.id"
              class="badge badge-default mr-1"
              >{{ tag.slice(0, -1) }}</span
            >
          </card>
          <h1 style="margin-left:80px; margin-bottom:40px">
            {{ question.AnswersList.length }} Answers
          </h1>
         
          <div v-for="(answer,index) in question.AnswersList" :key="answer.Id">
              <qanswer v-bind:index="index" 
              v-bind:answer="answer"
              v-bind:question="question">
              </qanswer>
          </div>

          <h1 style="margin-left:80px; margin-bottom:40px; margin-top:15px">
            Your Answer
          </h1>

          <form ref="profile_form" @submit.prevent="handleSubmit">
            <!-- <html-editor
              v-model="answ.Body"
              name="editor"
              style="margin-left:80px;"
            /> -->

               <vue-editor
                v-model="answ.Body"
                :editorToolbar="customToolbar"
                 style="margin-left:80px;"
              ></vue-editor>

            <base-button
              native-type="submit"
              type="button"
              class="btn btn-primary"
              style="margin-left:80px; margin-top:15px"
              >Post Your Answer</base-button
            >
          </form>
             </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import BaseInput from "@/components/Inputs/BaseInput.vue";
import BaseButton from "@/components/BaseButton.vue";
import { Select, Option, Tooltip } from "element-ui";
import moment from "moment";
import HtmlEditor from "@/components/Inputs/HtmlEditor";
import Comment from '@/components/Feed/Comment';
import Qanswer from '@/components/Feed/Qanswer';
import swal from "sweetalert2";
import "sweetalert2/dist/sweetalert2.css";
import { VueEditor } from "vue2-editor";

export default {
  layout: "DashboardLayout",

  components: {
    BaseInput,
    BaseButton,
    [Select.name]: Select,
    [Option.name]: Option,
    [Tooltip.name]: Tooltip,
    HtmlEditor,
    Comment,
    Qanswer,
    VueEditor
  },
 
  data() {
    return {
       customToolbar: [
        ["bold", "italic", "underline"],
        [{ list: "ordered" }, { list: "bullet" }],
        ["image", "code"]
      ],
      user:null,
      vote:null,
      loading: true,
      askedTimeAgo: null,
      editedTimeAgo: null,
      activityTimeAgo: null,
      questionOwner: "",
      qowner:"",
      question: {
        Body: "",
        Tags: "",
        Title: "",
        OwneruserId: "",
        AcceptedAnswerId: "",
        AnswersList: []
      },
      answ: {
        Body: "",
        OwnerUserId: "",
        ParentId: ""
      },
      comment: {
        PostId:  "",
        Text: "",
        UserId: ""
      },
      comm: [],
    };
  },

  created() {
    this.get();
    },
 
  methods: {
    async get() {
      try {
        
        this.user = await { ... this.$store.getters.currentUser };
        const id = this.$route.params.id;
        await this.$store.dispatch("questions/get", id);
        this.question = this.$store.getters["questions/question"];
        this.loading = false;


        const questionOwnerId = this.question.OwnerUserId;
   
        await this.$store.dispatch("users/get", questionOwnerId);
        this.questionOwner = await { ...this.$store.getters["users/user"] };

        this.askedTimeAgo = moment(this.question.CreationDate).fromNow();
        if(this.question.LastEditDate == '0001-01-01T00:00:00')
          this.editedTimeAgo = 0;
        else
        this.editedTimeAgo = moment(this.question.LastEditDate).fromNow();

      } catch (error) {
        console.log(error)
        this.$notify({
          type: "danger",
          message: `Oops, something went wrong!`
        });
      }
    },
     

    format_date(value) {
      if (value) {
        return moment(String(value)).format("MMM D 'YY, h:mm");
      }
    },

    async handleSubmit() {
      try {
        if(this.$store.getters.isAuthenticated == false)
          {
              this.$notify({
              type: "danger",
              message: "Ooops, login to post your answer!"
            });
          }
        else {
        this.answ.OwnerUserId = this.user.UserId;
        this.answ.ParentId = this.question.Id;
        this.answ.LastEditorDisplayName = this.user.DisplayName;
        await this.$store.dispatch("answers/add", this.answ);
       

        await this.$store.dispatch("questions/get", this.question.Id);
        this.question = this.$store.getters["questions/question"];

        this.answ.Body="";

        this.$notify({
          type: "success",
          message: "Answer added successfully."
        });
        }
      } catch (error) {
        this.$notify({
          type: "danger",
          message: "Oops, something went wrong!"
        });
      }
    },

  

    goBack() {
      this.$router.push({ name: "List Questions" });
    },

     async viewUser(Id) {
      this.$router.push({
        name: "View User",
        params: { id: Id }
      });
    },

    async deleteQuestion(id) {
      const confirmation = await swal.fire({
        title: `Are you sure?`,
        type: "question",
        buttonsStyling: false,
        showCancelButton: true,
        confirmButtonText: "Yes, delete it!",
        cancelButtonText: "No, keep it",
        confirmButtonClass: "btn btn-primary",
        cancelButtonClass: "btn btn-warning",
      });

      try {
        if (confirmation.value === true) {
          await this.$store.dispatch("questions/destroy", id);
          await this.goBack();
          this.$notify({
            type: "success",
            message: "Question deleted successfully.",
          });
        }
      } catch (error) {
        console.log(error)
          this.$notify({
            type: "danger",
            message: "Oops, something went wrong!",
          });
      }
    },

    async editQuestion(Id) {
      this.$router.push({
        name: "Edit Question",
        params: { id: Id }
      });
    },

     async UpVote(id) {
        if (this.$store.getters.isAuthenticated == false) {
          this.$notify({
            type: "danger",
            message: "Oops, login to upvote!"
          });
        } else {
       try{
      let v = { PostId: id, UserId: this.user.UserId, VoteTypeId: "2" };

      await this.$store.dispatch("questions/vote", v);

      await this.$store.dispatch("questions/get", id);
      this.question = this.$store.getters["questions/question"];
       }catch(error){
          this.$notify({
          type: "danger",
          message: "Oops, something went wrong!"
        });
       }
       }
    },

     async DownVote(id) {
        if (this.$store.getters.isAuthenticated == false) {
          this.$notify({
            type: "danger",
            message: "Oops, login to downvote!"
          });
        } else {
       try{
      let v = { PostId: id, UserId: this.user.UserId, VoteTypeId: "3" };

      await this.$store.dispatch("questions/vote", v);

      await this.$store.dispatch("questions/get", id);
      this.question = this.$store.getters["questions/question"];
       }catch(error){
          this.$notify({
          type: "danger",
          message: "Oops, something went wrong!"
        });
       }
        }
    },

  }
};
</script>
<style>
#delq:hover {
  color: red;
}
</style>
