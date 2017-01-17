var PartyListViewComponent = React.createClass({
  render: function() {
    var array = [];
    this.props.partyList.map(function(party,index) {
      array.push(
        <PartyListViewRowComponent
          id={party.id}
          key={index}
          name={party.name}
        />
      );
    });

    return (
      <div className="panel panel-default">
        <div className="panel-heading">
          <h3>Partijų sąrašas</h3>
        </div>
          <div className="panel-body">
            <a href="#/party/createParty"><button type="button" className="btn btn-success btn-sm">Registruoti</button></a>
            <span>
              <h5>Registruoti naują partiją </h5>
            </span>
          </div>
            <table className="table table-striped">
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
