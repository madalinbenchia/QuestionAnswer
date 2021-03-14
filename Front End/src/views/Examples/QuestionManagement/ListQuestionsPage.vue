<template>
  <div class="container-fluid mt-5">
      <div class="d-flex justify-content-between" style="margin-right:34%">
    <h1 class="ml-4">Top Questions</h1>
        <base-button type="info" icon size="lg" @click="addQuestion()">
          <span class="btn-inner--icon"><i class="far fa-clone"></i></span>
          <span class="btn-inner--text">Ask Question</span>
        </base-button>

        
        </div>
        <div class="d-flex justify-content-between">
          <div class="col-8">
            <div slot="empty" v-if="qloading">
              <img src="/img/loading.gif" style="height: 100px; width: 100px" />
            </div>
            <div v-else>
              <card
                v-for="question in this.questions"
                :key="question.id"
                class="no-border-card"
                body-classes="px-4 pb-2"
                footer-classes="pb-2"
              >
                <h3 slot="header" class="h3 mb-0">{{ question.title }}</h3>
                 <p v-if ="question.body.length > 300" class="card-text mb-4" v-html="question.Body.substring(0,300) + ' ...' "></p>
                 <p v-else class="card-text mb-4" v-html="question.body"></p>
                <span
                  v-for="tag in question.tags.split('<')"
                  :key="tag.id"
                  class="badge badge-default mr-1"
                  >{{ tag.slice(0, -1) }}</span
                >
                <a
                  type="text"
                  @click="viewQuestion(question.id)"
                  class="table-action"
                  data-toggle="tooltip"
                  style="cursor: pointer; float:right"
                >
                  See more
                </a>
              </card>
            </div>
          </div>

          <div class="col-4">
            <div slot="empty" v-if="uloading">
              <img src="/img/loading.gif" style="height: 100px; width: 100px" />
            </div>
            <div
              v-else
              style=" position: -webkit-sticky;
                      position: sticky;
                      top: 10px;"
            >
              <card
                gradient="success"
                header-classes="bg-transparent"
                footer-classes="bg-transparent"
                body-classes="px-lg-7"
                class="card-pricing border-0 text-center mb-4"
              >
                <h3 slot="header" class="h3 mb-0" style="text-align:center;">
                  Top Users <i class="fas fa-trophy"></i>
                </h3>
                <p
                  class="card-text mb-4"
                  v-for="(user, index) in this.users"
                  :key="user.id"
                  style=" white-space: nowrap;"
                >
                  <b>#{{ Number(index) + 1 }} </b>
                  <b @click="viewUser(user.UserId)" style="cursor:pointer">
                    <i clas="mr-1">  {{ user.DisplayName }} </i></b
                  >
                 : {{ user.Reputation }}  <i class="fas fa-star" style="color:#5e72e4;"></i>
                </p>
              </card>
            </div>
          </div>
        </div>
    </div>
  </div>
</template>
<script>
import { BasePagination } from "@/components";
import StatsCard from "@/components/Cards/StatsCard";
import {
  Table,
  TableColumn,
  DropdownMenu,
  DropdownItem,
  Dropdown,
  Tooltip,
  Select,
  Option,
  Button
} from "element-ui";
import swal from "sweetalert2";
import "sweetalert2/dist/sweetalert2.css";

export default {
  layout: "DashboardLayout",

  components: {
    StatsCard,
    BasePagination,
    [Tooltip.name]: Tooltip,
    [Table.name]: Table,
    [TableColumn.name]: TableColumn,
    [Dropdown.name]: Dropdown,
    [DropdownItem.name]: DropdownItem,
    [DropdownMenu.name]: DropdownMenu,
    [Select.name]: Select,
    [Option.name]: Option,
    [Button.name]: Button
  },

  data() {
    return {
      questions: [],
      users: [],
      qloading: true,
      uloading: true,
    };
  },

  created() {
    this.getQuestionsList();
    // this.getUsersList();
  },
  methods: {
    
    async getQuestionsList() {
      try {
        await this.$store.dispatch("questions/list");
        this.questions = await { ...this.$store.getters["questions/list"] };
        console.log(this.questions)
        this.qloading = false;
        console.log(this.qloading)
      } catch (error) {
        console.log(error);
        this.$notify({
          type: "danger",
          message: "Oops, something went wrong!"
        });
      }
    },

    async getUsersList() {
      try {
        let params = {};

        await this.$store.dispatch("users/list");
        this.users = await { ...this.$store.getters["users/list"] };
        this.uloading = false;
      } catch (error) {
        console.log(error);
        this.$notify({
          type: "danger",
          message: "Oops, something went wrong!"
        });
      }
    },

    async viewQuestion(Id) {
      this.$router.push({
        name: "View Question",
        params: { id: Id }
      });
    },
    addQuestion() {
      this.$router.push({ name: "Add Question" });
    },
     async viewUser(Id) {
      this.$router.push({
        name: "View User",
        params: { id: Id }
      });
    },
  }
};
</script>
