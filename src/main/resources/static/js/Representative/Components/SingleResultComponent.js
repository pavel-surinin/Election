var SingleResultComponent = React.createClass({
  render: function() {
    var list = [];
    var self = this;
    this.props.list.map(function(c, index){
      list.push(
        <SingleResultRowComponent
          key={index}
          candidate={c}
          registerVotes={self.props.registerVotes}
        />
      );
    });
    var errorMesages = validation.showMsg(this.props.errorMesages);
    return (
        <div className="panel panel-default col-md-12">
          <div className="row panel-heading">
            <div className="col-md-6">
              <h4>Vardas</h4>
            </div>
            <div className="col-md-6">
              <h4>Balsų skaičius</h4>
            </div>
          </div>
          <form onSubmit={this.props.onHandleSubmit}>
            <br />
            {list}
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
              <button type="submit" className="btn btn-success btn-outline">Registruoti</button>
          </form>
        </div>
    );
  }
});

window.SingleResultComponent = SingleResultComponent;
