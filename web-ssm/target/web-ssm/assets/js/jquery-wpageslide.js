$.fn.extend({
    wPageSlide: function (options) {
        var defaults = {
            width: $(window).width() + 5, //宽度：默认使用满屏
            height: $(window).height()
        };
        options = options || defaults;
        options.width = options.width || defaults.width;
        options.height = options.height || defaults.height;
        options.fullPage = options.fullPage || defaults.fullPage;

        var $body = $("body");
        var $wslide = $(this);
        var $wpages = $wslide.children("div#wpages");
        var $wpagesli = $wpages.children("div.wpage");
        var $wbtns = $wslide.children("ul#wpagebtns");
        var pagewidth = options.width;
        var pageheight = options.height;
        var windowwidth = $wpagesli.size() * pagewidth;
        var slidewidth = pagewidth;
        var currentindex = 0;
        var currentcls = "current";
        var delay = 300;
        if (options.width == defaults.width) { $body.css({ overflow: "hidden", margin: 0, padding: 0 }); }

        $wpages.css({ position: "relative", margin: 0, padding: 0, zIndex: 10 });
        $wslide.css({ position: "relative", overflow: "hidden", width: slidewidth, height: pageheight });

        $.each($wpagesli, function (index, val) {
            $(this).removeAttr("style");
            $(this).css({ position: "absolute", top: 0, left: pagewidth * index, width: pagewidth, height: pageheight, overflow: "auto" });

            $(".btn_detail").die().live("click", function () {
                var alt = $(this).attr("alt");
                var url = $(this).attr("url");

                if (alt == "back") {
                    index--;
                } else if ($wpagesli.length == index + 1) {
                    index = 1;
                } else {
                    index++;
                }
                var left = -(pagewidth * index);
                var oril = currentindex * pagewidth;
                left = left > 0 ? oril : Math.abs(left) >= windowwidth ? oril : left;
                pageSilde(left, delay, alt, url, index);
            })
        });



        //页面滑动
        function pageSilde(left, delay, alt, url, index) {

            $wpages.animate({ left: left }, delay, function () {
                //                var index = Math.round(Math.abs(left) / pagewidth);
                //                if (currentindex != index) {
                //                    currentindex = index;
                //                    $("#view" + index).html("1111");
                //                }
                //$("#view").html(alt);
                if (alt != "back") {
                    $("#view" + index).load(url);
                }
            });

        }

    }
});
