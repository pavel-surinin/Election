var PartyListViewComponent = React.createClass({
    componentDidMount: function(){
      $(document).ready(function() {
        $('#searchable-table').DataTable({
          language: {
            url: 'lithuanian.json'
          },
          responsive: true
        });
      });
    },
  render: function() {
    var succesCreateMessage = alerts.showSuccesFixed(this.props.succesCreateText);
    var deletePartyMessage = alerts.showDangerFixed(this.props.deletedPartyText);
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
                {deletePartyMessage}
              </div>
                <table className="table table-striped table-hover" id="searchable-table">
                  <thead>
                    <tr>
                      <th>
                        Nr.
                      </th>
                      <th>
                        Partija
                      </th>
                      <th>
                        Veiksmai
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
