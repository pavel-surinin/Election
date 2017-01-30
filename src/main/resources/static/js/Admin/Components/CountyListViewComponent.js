
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
          districts={county.districts}
          candidates={county.candidates}
          parties={county.parties}
          onHandleDelete={self.onHandleDelete}
        />
      );
    });

    return (
		      <div className="panel panel-default">
		        <div className="panel-heading">
		          <h3> Rinkimų apygardų sąrašas </h3>
		        </div>
		          <div className="panel-body">
                {succesCreateMessage}
		            <h5>
		              <a href="#/admin/county/create">
		                <button type="button" className="btn btn-success btn-sm">
		                  Registruoti naują Apygarda
		                </button>
		              </a>
		            </h5>
		          </div>
		            <table width="100%" className="table table-striped table-bordered table-hover" id="dataTables-example">
		              <thead>
		                <tr>
		                  <th> Nr </th>
		                  <th> Apygarda </th>
		                  <th> Detali informacija </th>
		                  <th> &nbsp; Redaguoti &nbsp; </th>
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
