var MultiResultRowComponent = React.createClass({
  onInputChange : function(event){
    var votes = event.target.value;
    var pid = this.props.party.id;
    this.props.registerVotes(pid, votes);
  },
  render: function() {
    var self = this;
    var p = this.props.party;
    var candidates = p.members.map(function(c,index){
      if (c.multiList == true) {
        return <MultiResultRowModalRowComponent onChangePartyRating={self.props.onChangePartyRating} key={index} info={c} />;
      }
    });
    return (
      <div style={{paddingTop : '2px'}} className='row text-primary'>
        <div className='col-md-1 small'>
          <h5>{p.partyNumber}</h5>
        </div>
        <div className='col-md-5 small'>
          <h5>{p.name}</h5>
        </div>
        <div style={{float : 'left'}} className='input-group col-md-3 small'>
          <input onChange={this.onInputChange} type='number' className='form-control' placeholder='Balsų skaičius' aria-describedby='basic-addon2' required/>
          <span className='input-group-addon' id='basic-addon2'>vnt.</span>
        </div>
        <div id='modal-col' className='input-group col-md-3 small'>
          &nbsp;
          <button
          id={'rating-modal-' + p.id}
          type='button'
          className='btn btn-primary btn-outline'
          data-toggle='modal'
          data-target={'#rating' + p.id}
          >
          <i className="fa fa-star-o" aria-hidden="true"></i>&nbsp;
            Reitinguok
          </button>
          <div id={'rating' + p.id} className='modal fade' role='dialog'>
          <div className='modal-dialog'>

          <div className='modal-content'>
          <div className='modal-header'>
          <button type='button' className='close' data-dismiss='modal'><i className="fa fa-times" aria-hidden="true"></i></button>
          &nbsp;
          <button style={{marginRight : '10px'}} type='button' className='close'><i className="fa fa-question" aria-hidden="true"></i></button>
            <h4 className='modal-title'>{p.partyNumber} {p.name}
            </h4>
          </div>
          <div className='modal-body'>
          {candidates}
          </div>
          <div className='modal-footer'>
          <button type='button' className='btn btn-default' data-dismiss='modal'>Close</button>
          </div>
          </div>

          </div>
          </div>
        </div>

      </div>
    );
  }

});

window.MultiResultRowComponent = MultiResultRowComponent;
