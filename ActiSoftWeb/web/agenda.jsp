<!DOCTYPE html>
<html lang="en">

<head>
    <%@include  file="tpl_head.jsp" %>
    <link type="text/css" rel="stylesheet" href="bower_components/fullcalendar/fullcalendar.min.css">
</head>

<body>

    <div id="wrapper">

        <%@include file="tpl_navbar.jsp" %>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Agenda <span data-toggle="modal" data-target="#mdlAgenda" class="btn btn-primary"><span class="fa fa-file-o fa-fw"> </span>Nuevo</a></h1>
                    
                </div>

            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Listado de Eventos
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div id="calendar">                               
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- DataTables JavaScript -->
    <script src="bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>
    
    <script src="bower_components/datatables-responsive/js/dataTables.responsive.js"></script>
    
    <script src="bower_components/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>    
    <script src="bower_components/bootstrap-datepicker/js/locales/bootstrap-datepicker.es.min.js"></script>        

    <script src="js/bootbox.min.js"></script>
    <script src="bower_components/jquery-mask/jquery.mask.min.js"></script>    
    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>
    <script src="js/moment-with-locales.min.js"></script>
    
    <script src="bower_components/fullcalendar/fullcalendar.min.js"></script>
    <script src="bower_components/fullcalendar/lang/es.js"></script>
    
    <script src="js/invesoft.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    var arrEvents = [{
                            title: 'All Day Event',
                            start: '2015-02-01'
                    },
                    {
                            title: 'Long Event',
                            start: '2015-02-07',
                            end: '2015-02-10'
                    },
                    {
                            id: 999,
                            title: 'Repeating Event',
                            start: '2015-02-09T16:00:00'
                    },
                    {
                            id: 998,
                            title: 'Repeating Event',
                            start: '2015-02-16T16:00:00'
                    },
                    {
                            title: 'Conference',
                            start: '2015-02-11',
                            end: '2015-02-13'
                    },
                    {
                            title: 'Meeting',
                            start: '2015-02-12T10:30:00',
                            end: '2015-02-12T12:30:00'
                    },
                    {
                            title: 'Lunch',
                            start: '2015-02-12T12:00:00'
                    },
                    {
                            title: 'Meeting',
                            start: '2015-02-12T14:30:00'
                    },
                    {
                            title: 'Happy Hour',
                            start: '2015-02-12T17:30:00'
                    },
                    {
                            title: 'Dinner',
                            start: '2015-02-12T20:00:00'
                    },
                    {
                            title: 'Birthday Party',
                            start: '2015-02-13T07:00:00'
                    },
                    {
                            title: 'Click for Google',
                            url: 'http://google.com/',
                            start: '2015-02-28'
                    }
                ];
    $(document).ready(function() {
//        $('#mdlAgenda').modal('show');
        
        $('#calendar').fullCalendar({
			defaultDate: moment().format("YYYY-MM-DD"),
			editable: true,
			eventLimit: true, // allow "more" link when too many events
			events: function(start, end, timezone, callback) {                                
                                loadData({},callback);                             
                        }
				
		});
    });
    function loadData(data,callback){
        $.ajax({
               url: '<%= PathCfg.AGENDA_LIST %>',
               data: data,
               method:"POST",
               dataType: "json",
                beforeSend:function(){                    
               },
               success: function(data) {
                   var events = [];
                   if(data.Result === "OK") {
                       callback(createEvents(data.Records));
                   }
                   
               }
           });
    }
    function borraEvento(){
        var id = $(this).data('index');
        var $tr = $(this).parent().parent();
        deleteData('<%= PathCfg.CLIENTE_DEL %>',{id:id},function(result) {
                if(result.Result === "OK") {
                    $tr.remove();
                } else if (result.Message) bootbox.alert(result.Message);
        });
    }
    function createEvents(data){
        var events = [];
        for(var i = 0;i< data.length;i++){
            d = data[i];
            events.push({
                        title: d.titulo,
                        start: d.desde,
                        end:   d.hasta
                    });    
       }
       
       return events;
    }
   
        </script>
    </script>
    
<%@include file="agenda_edit_mdl.jsp"%>
<%@include file="tpl_footer.jsp"%>
</body>

</html>
