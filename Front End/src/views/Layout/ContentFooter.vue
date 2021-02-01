<template>
  <footer class="footer px-4 mx-2">
    <div class="row align-items-center justify-content-lg-between">
      <ul class="nav nav-footer">
        <li class="nav-item">
          <a
            href="https://unibuc.ro/?lang=en"
            class="nav-link"
            target="_blank"
            rel="noopener"
            >UNIBUC</a
          >
        </li>
        <li class="nav-item">
          <a
            href="https://fmi.unibuc.ro/en"
            class="nav-link"
            target="_blank"
            rel="noopener"
            >FMI</a
          >
        </li>
        <li class="nav-item">
          <a
            href="https://moodle.unibuc.ro/course/info.php?id=3332"
            class="nav-link"
            target="_blank"
            rel="noopener"
            >MODELAREA SISTEMELOR SOFTWARE</a
          >
        </li>
      </ul>
      <div class="col-lg-6">
        <div class="copyright text-center text-lg-right text-muted">
          © {{ year }}, made with <i class="fa fa-heart heart"></i> by<a
            href="https://www.linkedin.com/in/madalin-benchia-99b934172/"
            class="font-weight-bold ml-1"
            target="_blank"
            rel="noopener"
            >Madalin Benchia</a
          >
          ,<a
            href="https://www.linkedin.com/in/madalina-caea-60a969193/"
            class="font-weight-bold ml-1"
            target="_blank"
            rel="noopener"
            >Madalina Caea</a
          >

          and<a
            href="https://www.linkedin.com/in/alexandru-andrei-dumitru-654949193/"
            class="font-weight-bold ml-1"
            target="_blank"
            rel="noopener"
            >Alexandru Dumitru</a
          >
        </div>
      </div>
    </div>
  </footer>
</template>
<script>
export default {
  data() {
    return {
      year: new Date().getFullYear()
    };
  }
};
</script>
<style></style>
