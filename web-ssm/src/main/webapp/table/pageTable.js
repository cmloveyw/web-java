var pageTable={
		//分页
		Page : function(currentPage,size,totalElements,numberOfElements,totalPages){
			var pager = "<div class=\"row \" ><div class=\"col-sm-6\"><div class=\"dataTables_info\" id=\"sample-table-2_info\" style=\"padding:10px 0 0 0;\">";
	        pager += "显示 "
	                + (currentPage * size + 1)
	                + " 到 "
	                + (currentPage * size + numberOfElements)
	                + " 条 共 " + totalElements
	                + " 条记录";
	        pager += "</div></div><div class=\"col-sm-6\">";
	        pager += "<div class=\"dataTables_paginate paging_bootstrap\">";
	        pager += "<ul class=\"pagination\">";
	        pager += "<li ><a href=\"javascript:void(0)\" onclick=\"findList(0)\"><i class=\"icon-double-angle-left\"></i></a></li>";
	        if((currentPage-1)<0){
	            pager += "<li><a href=\"javascript:void(0)\" onclick=\"findList("
	                + (currentPage)
	                + ")\"><i class=\"icon-angle-left\"></i></a></li>";
	        }else{
	        pager += "<li ><a href=\"javascript:void(0)\" onclick=\"findList("
	                + (currentPage - 1)
	                + ")\"><i class=\"icon-angle-left\"></i></a></li>";
	        }
	        pager += "<li class=\"prev disabled\"><a href=\"#\">"
	                + (currentPage + 1)
	                + "/"
	                + totalPages + "</a></li>";
	        if((currentPage + 1)>(totalPages-1)){
	            pager += "<li><a href=\"javascript:void(0)\" onclick=\"findList("
	                + (totalPages-1)
	                + ")\"><i class=\"icon-angle-right\"></i></a></li>";
	        }else{
	            pager += "<li><a href=\"javascript:void(0)\" onclick=\"findList("
	                + (currentPage + 1)
	                + ")\"><i class=\"icon-angle-right\"></i></a></li>";
	        }
	        pager += "<li ><a href=\"javascript:void(0)\" onclick=\"findList("
	                + (totalPages-1)
	                + ")\"><i class=\"icon-double-angle-right\"></i></a></li>";
	        pager += "</ul></div></div></div>";
	        return pager;
		}
}