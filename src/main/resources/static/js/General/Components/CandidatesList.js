function getCandidateTable(self){
  var candidateTable = [];
  candidateTable = self.props.candidateList.map(function(cand,index){
    return (
      <tr key={index} className='small'>
        <td>
           {cand.numberInParty}
        </td>
        <td>
           {cand.name}
        </td>
        <td>
           {cand.surname}
        </td>
        <td>
           {cand.birthDate}
        </td>
        <td>
           <a href={'#/party/' + cand.partijosId}>{cand.partijosPavadinimas}</a>
        </td>
        <td>
          <a href={'#/candidate/' + cand.id} ref="info" title="Detaliau" id={'description-button-' + cand.id}
          className="btn btn-info btn-outline btn-sm fa fa-info">
          </a>
        </td>
      </tr>
    );
  });
  return candidateTable;
}

var CandidatesList = React.createClass({
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
    var candidateTable = getCandidateTable(this);
    return (
        <div>
          <div className="container">
            <h1 className='yellow'>Kandidatų sąrašas</h1>
          </div>
          <div id="exTab1" className="container shadow">
            <div className="tab-content clearfix">
              <table width="100%" className="table table-striped" id="searchable-table">
                <thead>
                  <tr>
                    <th>Nr.</th>
                    <th>Vardas</th>
                    <th>Pavardė</th>
                    <th>Gimimo data</th>
                    <th>Partija</th>
                    <th>Detali informacija</th>
                  </tr>
                </thead>
                <tbody>
                  {candidateTable}
                </tbody>
              </table>
            </div>
          </div>
        </div>
    );
  }
});

window.CandidatesList = CandidatesList;
