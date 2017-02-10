
var CountyListViewComponent = React.createClass({
  render: function() {
    var succesCreateMessage = alerts.showSucces(this.props.succesCreateText);
    var array = [];
    var self= this.props;
    this.props.countyList.map(function(county,index) {
      array.push(
        <CountyListRowViewComponent
          id={county.id}
          key={index}
          name={county.name}
          members={county.candidatesCount}
          districts={county.districts}
          candidates={county.candidates}
          parties={county.parties}
          onHandleDelete={self.onHandleDelete}
          onFileChange={self.onFileChange}
          onHandleFormAddSingleCandSubmit={self.onHandleFormAddSingleCandSubmit}
          fileErrorMesages={self.fileErrorMesages}
          onHandleAddClick={self.onHandleAddClick}
          succesMessage={self.succesMessage}
        />
      );
    });

    return (
		      <div className="panel panel-default">
		        <div className="panel-heading" style={{paddingTop:20,paddingBottom:20}}>
		          <h4 style={{display:'inline'}}><i className="fa fa-table"></i>&nbsp; Rinkimų apygardų sąrašas </h4>
              <div className=" pull-right">
                <a href="#/admin/county/create" id="register-button" className="text-success"><i className="fa fa-plus"></i>
                  &nbsp;
                  Registruoti
                </a>
              </div>
		        </div>
		          <div className="panel-body">
                {succesCreateMessage}
		          </div>

		            <table width="100%" className="table table-striped table-hover" id="dataTables-example">
		              <thead>
		                <tr>
		                  <th> Nr. </th>
		                  <th> Apygarda </th>
		                  <th> Veiksmai </th>
		                </tr>
		              </thead>
		              <tbody>
		               {array}
		              </tbody>
		            </table>

		        </div>
    );
  }

});

window.CountyListViewComponent = CountyListViewComponent;
