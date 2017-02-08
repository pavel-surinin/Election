var PartyListViewComponent = React.createClass({
  render: function() {
    var succesCreateMessage = alerts.showSucces(this.props.succesCreateText);
    var array = [];
    var self = this;
    this.props.partyList.map(function(party,index) {
      array.push(
        <PartyListViewRowComponent
          id={party.id}
          key={index}
          name={party.name}
          partyNumber={party.partyNumber}
          onHandleDelete={self.props.onHandleDelete}
        />
      );
    });

    return (

		      <div className="panel panel-default">
		        <div className="panel-heading" style={{paddingTop:20,paddingBottom:20}}>
		          <h4 style={{display:'inline'}}><i className="fa fa-table"></i>&nbsp; Partijų sąrašas</h4>
              <div className="text-success pull-right">
                <a href="#/admin/party/create"  id="register-button" className="text-success"><i className="fa fa-plus"></i>
                  &nbsp; Registruoti
                </a>
              </div>
		        </div>
		          <div className="panel-body">
                {succesCreateMessage}
		          </div>
		            <table className="table table-striped table-hover">
		              <thead>
		                <tr>
		                  <th>
		                    Nr
		                  </th>
		                  <th>
		                    Partija
		                  </th>
		                  <th>
		                    Redaguoti
		                  </th>
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

window.PartyListViewComponent = PartyListViewComponent;
