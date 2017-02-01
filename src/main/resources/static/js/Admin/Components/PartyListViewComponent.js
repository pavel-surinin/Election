var PartyListViewComponent = React.createClass({
  render: function() {
    var succesCreateMessage = alerts.showSucces(this.props.succesCreateText);
    var array = [];
    this.props.partyList.map(function(party,index) {
      array.push(
        <PartyListViewRowComponent
          id={party.id}
          key={index}
          name={party.name}
          partyNumber={party.partyNumber}
        />
      );
    });

    return (

		      <div className="panel panel-default">
		        <div className="panel-heading">
		          <h3>Partijų sąrašas</h3>
		        </div>
		          <div className="panel-body">
                {succesCreateMessage}
		            <a href="#/admin/party/create"><button type="button" id="register-button" className="btn btn-success btn-sm">Registruoti</button></a>
		            <span>
		              <h5>Registruoti naują partiją </h5>
		            </span>
		          </div>
              <div className="panel-body">
		            <table className="table table-striped table-bordered table-hover">
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
		        </div>
    );
  }

});

window.PartyListViewComponent = PartyListViewComponent;
