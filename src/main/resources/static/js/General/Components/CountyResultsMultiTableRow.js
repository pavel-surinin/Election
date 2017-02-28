var CountyResultsMultiTableRow = React.createClass({
  componentDidMount: function() {
    $(this.refs.ratingDisabled).tooltip();
    $(this.refs.modal).tooltip();
  },
  render: function() {
    var p = this.props.info;
    if (p.ratings.length != 0) {
      return(
      <div>
        <a data-toggle="tooltip" data-placement="left" title='Reitingai' ref='modal' type="button" className="btn btn-info btn-outline btn-sm" data-toggle="modal" data-target={'#ratingModal' + p.par.id}>
          <i className="fa fa-star" aria-hidden="true"></i>
        </a>
        <div id={'ratingModal' + p.par.id} className="modal fade" role="dialog">
          <div className="modal-dialog">
            <div className="modal-content" style={{margin : 'auto'}}>
              <div className="modal-header">
                <button type="button" className="close" data-dismiss="modal">&times;</button>
                <h4 className="modal-title">Reitingai - {p.par.name}</h4>
              </div>
              <div className="modal-body">
                <table className="table table-hover">
                  <thead>
                    <tr>
                      <th className='col-md-2'>Nr.</th>
                      <th className='col-md-8'>Vardas Pavardė</th>
                      <th className='col-md-2'>Balų kiekis</th>
                    </tr>
                  </thead>
                  <tbody>
                    {p.ratings.map(function(rating,index){
                      return(
                          <tr key={index}>
                            <td>
                              {rating.candidate.numberInParty}
                            </td>
                            <td>
                              {rating.candidate.name} {rating.candidate.surname}
                            </td>
                            <td>
                              {rating.votes}
                            </td>
                          </tr>
                      );
                    })}
                  </tbody>
                </table>
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-default" data-dismiss="modal">Close</button>
              </div>
            </div>
            </div>
          </div>
        </div>
      );
    }else{
      return (
        <div>
        <a id='rating-disabled' ref='ratingDisabled' data-toggle="tooltip" data-placement="left" title='Reitingų nėra'
        className='btn btn-info btn-outline btn-sm rating-disabled' disabled={true}><i className="fa fa-star-o" aria-hidden="true"></i></a>
        </div>
      );
    }
  },

});

window.CountyResultsMultiTableRow = CountyResultsMultiTableRow;
