<template>
  <div class="container-fluid mt-5">
      <div>
      
     
        <div class="d-flex justify-content-between">
          <div class="col-12">
            <div slot="empty" v-if="loading">
              <img src="/img/loading.gif" style="height: 100px; width: 100px" />
            </div>
            <div v-else>
                <div v-if="Object.keys(this.questions).length == 0">  <img src="/img/brand/ono.png" style=" display: block;
  margin-left: auto;
  margin-right: auto;" />

     <base-button style=" display: block;
  margin-left: auto;
  margin-right: auto; margin-top: 35px"  type="primary" icon size="lg" @click="addQuestion()">
          <span class="btn-inner--icon"><i class="fas fa-user-edit"></i></span>
          <span class="btn-inner--text">Add Question</span>
        </base-button>
        </div>
              <card
                v-for="question in this.questions"
                :key="question.id"
                class="no-border-card"
                body-classes="px-4 pb-2"
                footer-classes="pb-2"
              >
                <h3 slot="header" class="h3 mb-0">{{ question.Title }}</h3>
                <p v-if ="question.Body.length > 300" class="card-text mb-4" v-html="question.Body.substring(0,300) + ' ...' "></p>
                 <p v-else class="card-text mb-4" v-html="question.Body"></p>
                <span
                  v-for="tag in question.Tags.split('<')"
                  :key="tag.id"
                  class="badge badge-default mr-1"
                  >{{ tag.slice(0, -1) }}</span
                >
                <a
                  type="text"
                  @click="editQuestion(question.Id)"
                  class="table-action"
                  data-toggle="tooltip"
                  style="cursor: pointer; float:right"
                >
                  See more
                </a>
              </card>
            </div>
          </div>


        </div>
      </div>
    </div>
  </div>
</template>
<script>
import BaseInput from "@/components/Inputs/BaseInput.vue";
import BaseButton from "@/components/BaseButton.vue";
import { Select, Option } from "element-ui";
import HtmlEditor from "@/components/Inputs/HtmlEditor";

export default {
  layout: "DashboardLayout",

  components: {
    BaseInput,
    BaseButton,
    [Select.name]: Select,
    [Option.name]: Option,
    HtmlEditor
  },

  data() {
    return {
      id: "",
      loading: true,
      questions: null,
    };
  },

  created() {
    this.get();
  },
  watch: {
    $route() {
      this.get();
    }
  },

  methods: {
    async get() {
      try {
        this.id = this.$route.params.id;
        await this.$store.dispatch("questions/search", this.id);
        this.questions = await { ...this.$store.getters["questions/search"] };
        console.log(this.questions);
        this.loading = false;

        // this.askedTimeAgo = moment(this.question.CreationDate).fromNow();
        // this.activityTimeAgo = moment(this.question.LastActivityDate).fromNow();
      } catch (error) {
        this.$notify({
          type: "danger",
          message: `Oops, something went wrong!`
        });
        console.log(error);
      }
    },

    async handleSubmit() {
      try {
        await this.$store.dispatch("answers/add", this.answer);

        this.$notify({
          type: "success",
          message: "Answer added successfully."
        });
        this.goBack();
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
    addQuestion() {
      this.$router.push({ name: "Add Question" });
    },
    async editQuestion(Id) {
      this.$router.push({
        name: "View Question",
        params: { id: Id }
      });
    },
  }
};
</script>
