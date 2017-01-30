var ResultsListContainer = React.createClass({
  getInitialState: function() {
    return {
      isLoading : false,
    };
  },
  componentWillMount: function() {
      console.log(this.state);
  },
  render: function() {
      if (this.state.isLoading) {
        return (
          <div>
            <img src='./Images/loading.gif'/>
          </div>
        );
      } else {
        return(
          <ResultsListComponent />
          );
        }
  }
});

window.ResultsListContainer = ResultsListContainer;
