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
           {cand.partijosPavadinimas}
        </td>
        <td>
          <button ref="info" title="Aprašymas" id={'description-button-' + cand.id}
          type="button"  className="btn btn-info btn-sm fa fa-info" data-toggle="modal" data-target={'#' + cand.id}></button>
           <div id={cand.id} className="modal fade" role="dialog">
            <div className="modal-dialog">
              <div className="modal-content" style={{margin : 'auto'}}>
                <div className="modal-header">
                  <button type="button" id="modal-close-button" className="close" data-dismiss="modal">&times;</button>
                  <h4 className="modal-title">{cand.name} {cand.surname}</h4>
                </div>
                <div className="modal-body">
                  {cand.description}
                </div>
                <div className="modal-footer">
                  <button type="button" id="close-button"  className="btn btn-default" data-dismiss="modal">Uždaryti</button>
                </div>
              </div>
            </div>
          </div>
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
