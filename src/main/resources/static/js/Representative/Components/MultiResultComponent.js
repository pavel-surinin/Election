var MultiResultComponent = React.createClass({
  render: function() {
    var self = this;
    var parties = [];
    this.props.list.map(function(p,index){
      parties.push(
      <MultiResultRowComponent onChangePartyRating={self.props.onChangePartyRating} registerVotes={self.props.registerVotes} key={index} party={p} />
    );
    });
    var errorMesages = validation.showMsg(this.props.errorMesages);
    return (

      <div>
        <div className="panel panel-default col-md-12">
          <div className="row panel-heading">
            <div className="col-md-1">
              <h4>Nr.</h4>
            </div>
            <div className="col-md-5">
              <h4>Partija</h4>
            </div>
            <div className="col-md-6">
              <h4>Balsų skaičius</h4>
            </div>
          </div>
          <form onSubmit={this.props.onHandleSubmit}>
            <br/>
            {parties}
            <div style={{padding : '2px 0px'}} className='row bg-warning'>
              <div className='col-md-5 small'>
                <h5><b>Sugadinti biuleteniai</b></h5>
              </div>
              <div className='col-md-1 small'>
                <h5></h5>
              </div>
              <div style={{float : 'left'}} className='input-group col-md-3 small'>
                <input onChange={this.props.onHandleSpoiledChange} type='number' className='form-control' placeholder='Skaičius' aria-describedby='basic-addon2' required/>
                <span className='input-group-addon' id='basic-addon2'>vnt.</span>
              </div>
            </div>
              <br/>
              {errorMesages}
              <button style={{marginTop : '-8px'}} type="submit" className="btn btn-success btn-outline">Registruoti</button>
          </form>
        </div>
      </div>
    );
  }
});

window.MultiResultComponent = MultiResultComponent;
