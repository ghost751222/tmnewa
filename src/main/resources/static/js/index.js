  var app = new Vue({
            "el":"#app",
             data:{
                title:"VMS Page",
                perPage :10,
                baseUrl:null,
                url:null,
                dataPath :"data",
                paginationPath :"pagination",
                appendParams:{},
                startTime:"",
                endTime:"",
                ani:"",
                dnis:"",
                comment:"",
                edit_UniCallID:"",
                edit_Comment:"",
                showModal: false,
                sortOrder : [{field:"StartTime",direction:"asc"}],
                fields: [
                    //{ "name": "CallID", "title": "CallID" },
                    { "name": "UniCallID", "title": "UniCallID",sortField: "UniCallID" },
                    { "name": "StartTime", "title": "StartTime",sortField: "StartTime" },
                    { "name": "EndTime", "title": "EndTime",sortField: "EndTime" },
                    { "name": "TransExt", "title": "Ext.",sortField: "TransExt" },
                    { "name": "ANI", "title": "ANI",sortField: "ANI" },
                    //{ "name": "DNIS", "title": "DNIS",sortField: "DNIS" },
                    { "name": "Comment", "title": "Comment",sortField: "Comment" },
                    { "name": "VMrecord", "title": "VMRecord",sortField: "VMRecord" },
                    { "name": "actions", "title": "Actions" },

                ],
             },
             methods:{
                selectChange:function(e){
                    var value = e.target.value;
                    var dt = new Date();
                    dt.setDate(dt.getDate() - value)
                    var year = dt.toLocaleDateString("en-us", { year: "numeric" });
                    var month = dt.toLocaleDateString("en-us", { month: "2-digit" });
                    var day = dt.toLocaleDateString("en-us", { day: "2-digit" });
                    var formattedDate = year + "-" + month + "-" + day;
                    this.startTime = formattedDate
                    this.query()
                },
                query:function(e){


                     if(this.startTime == ""){
                        alert('PLease type StartTime (From)')
                        this.$refs.startTime.focus()
                        return
                     }

                    if(this.endTime == ""){
                        alert('PLease type StartTime (To)')
                        this.$refs.endTime.focus()
                        return
                    }



                    this.appendParams= {}
                    this.appendParams["startTime"] = this.startTime
                    this.appendParams["endTime"] = this.endTime
                    this.appendParams["ani"] = this.ani == "" ? null : this.ani;
                    this.appendParams["dnis"] = this.dnis  == "" ? null : this.dnis;
                    this.appendParams["comment"] = this.comment  == "" ? null : this.comment;

                    this.$nextTick(function(){
                        this.$refs.vuetable.refresh()
                    });


                },
                transformData :function(data){

                    var transformed = {}
                    var pageIndex = data.number
                    var total = data.totalElements
                    var pageSize = data.size
                    var from = pageIndex * pageSize + 1
                    var to = (pageIndex +1 ) * pageSize
                    var lastPage = data.totalPages
                    var currentPage = pageIndex +1
                    transformed.data = data.content
                    transformed.pagination = {
                        total: total,
                        per_page: pageSize,
                        current_page: currentPage,
                        last_page: lastPage,
                        next_page_url: this.url,
                        prev_page_url: this.url,
                        from: from,
                        to: to
                   }

                    return transformed
                },onPaginationData:function(paginationData){
                    this.$refs.pagination.setPaginationData(paginationData);
                    this.$refs.paginationInfo.setPaginationData(paginationData);


                },onChangePage(page){
                  this.$refs.vuetable.changePage(page);
                },onActionClicked:function(action,data){
                  var _this = this
                  if(action == "play"){
                         var vmRecord = data.VMrecord
                                           var url = this.baseUrl  + "attachment?fileName=" +  vmRecord
                                           axios.get(url).then(res=>{
                                             var data = res.data
                                             var src = "data:audio/mp3;base64," + data
                                             this.$refs.source.src= src
                                             this.$refs.audio.load()
                                            this.$refs.audio.onloadeddata = function(){
                                                _this.$refs.audio.play()
                                            }

                                           }).catch( (error) => {
                                                   console.error(error)
                                           });
                  }else if(action =="edit"){
                       this.edit_UniCallID = data.UniCallID
                        this.edit_Comment =data.Comment
                        this.showModal = true
                  }


                },save(){
                    var url = this.baseUrl
                    var data={}
                    data["UniCallID"]=this.edit_UniCallID
                    data["Comment"]=this.edit_Comment
                    axios.post(url,data).then(res=>{
                       var _data = res.data
                       if(_data==0){
                            this.$refs.vuetable.reload()
                             this.showModal = false
                       }else if (_data == 1){
                            alert('Save Data Failed')
                       }
                    }).catch( (error) => {
                        alert('Save Data Failed')
                        console.error(error)
                    });
                },loadSuccess(res){
                   if(res.request.responseURL.includes('login')){
                         location.href = baseUrl +  "login"
                   }
                }
             },mounted:function(){


                var dt = new Date();
                var year = dt.getFullYear();
                var month = (dt.getMonth()+1).toString().padStart(2, '0')

                //var date = dt.getDate().toString().padStart(2, '0')

                var lastMonth = new Date(year, month, 0).getMonth().toString().padStart(2, '0')
                var lastDayOfMonth = new Date(year, month, 0).getDate();

                this.startTime = `${year}-${lastMonth}-01`
                this.endTime = `${year}-${month}-${lastDayOfMonth}`

                this.baseUrl = baseUrl
                this.url  = this.baseUrl + "data"
                this.query();

                var _this = this
                document.addEventListener("keypress", function(event) {
                  if (event.key === "Enter") {
                    event.preventDefault();
                     _this.query();
                  }
                });

             },created:function(){
                    document.title= this.title;

             }
       })