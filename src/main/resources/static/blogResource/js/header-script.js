$(function (){
    let local_time = new Date().getHours();
    console.log(local_time)
    if(0<local_time&&local_time<5){
        $("header").css("background-image","url('/static/blogResource/img/timeBG/dapo-oni-64tVc0A2_xQ-unsplash.jpg')");
    }else if(5<local_time&&local_time<8){
        $("header").css("background-image","url('/static/blogResource/img/timeBG/vincentiu-solomon-ln5drpv_ImI-unsplash.jpg')");
    }else if(8<local_time&&local_time<16){
        $("header").css("background-image","url('/static/blogResource/img/timeBG/adeolu-eletu-ohh8ROaQSJg-unsplash.jpg')");
    }else if(16<local_time&&local_time<19){
        $("header").css("background-image","url('/static/blogResource/img/timeBG/vincent-m--hlJ9gwSl20-unsplash.jpg')");
    }else{
        $("header").css("background-image","url('/static/blogResource/img/timeBG/rafael-zuniga-8KvjXZ7sD78-unsplash.jpg')");
    }
}

)