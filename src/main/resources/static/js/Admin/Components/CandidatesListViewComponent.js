var CandidatesListViewComponent = React.createClass({

    componentDidMount: function(){
        $(document).ready(function() {
          $('#searchable-table').DataTable({
            language: {
                url: 'lithuanian.json'
            },
            responsive: true
          });
//            $('#searchable-table_length').css("background-color", "yellow");
        });
    },

  render: function() {
	      var succesCreateMessage = alerts.showSuccesFixed(this.props.succesCreateText);
	      var array = [];
      this.props.candidateList.map(function(cand,index) {
        array.push(
          <CandidatesListViewRowComponent
            id={cand.id}
            key={index}
            name={cand.name}
            surname={cand.surname}
            birthDate={cand.birthDate}
            partijosId={cand.partijosId}
            partijosPavadinimas={cand.partijosPavadinimas}
            description={cand.description}
            numberInParty={cand.numberInParty}
          />
        );
      });
    return (
          <div className="panel panel-default">
          <div className="panel-heading">
              <h4><i className="fa fa-table"></i>&nbsp; Kandidatų sąrašas</h4>
          </div>
          <div className="panel-body">
          </div>
            <table width="100%" className="table table-striped" id="searchable-table">
             <thead>
              <tr>
                <th>Nr. partijos sąraše</th>
                <th>Vardas</th>
                <th>Pavardė</th>
                <th>Gimimo data</th>
                <th>Partija</th>
                <th>Veiksmai</th>
              </tr>
            </thead>
            <tbody>
              {array}
            </tbody>
          </table>
          {succesCreateMessage}
      </div>


    );
}
});

window.CandidatesListViewComponent = CandidatesListViewComponent;
